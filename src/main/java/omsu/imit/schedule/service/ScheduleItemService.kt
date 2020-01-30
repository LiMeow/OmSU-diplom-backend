package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateScheduleItemRequest
import omsu.imit.schedule.dto.response.ScheduleForLecturer
import omsu.imit.schedule.dto.response.ScheduleItemInfo
import omsu.imit.schedule.dto.response.ScheduleItemInfoForLecturer
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.*
import omsu.imit.schedule.repository.ScheduleItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ScheduleItemService
@Autowired
constructor(private val auditoryService: AuditoryService,
            private val auditoryOccupationService: AuditoryOccupationService,
            private val disciplineService: DisciplineService,
            private val groupService: GroupService,
            private val lecturerService: LecturerService,
            private val timeBlockService: TimeBlockService,
            private val scheduleService: ScheduleService,
            private val scheduleItemRepository: ScheduleItemRepository) : BaseService() {

    fun createScheduleItem(scheduleId: Int, request: CreateScheduleItemRequest): ScheduleItemInfo {
        val schedule: Schedule = scheduleService.getScheduleById(scheduleId)
        val timeBlock: TimeBlock = timeBlockService.getTimeBlockById(request.timeBlockId)
        val auditory: Auditory = auditoryService.getAuditoryById(request.auditoryId)
        val discipline: Discipline = disciplineService.getDiscipline(request.disciplineId)
        val lecturer: Lecturer = lecturerService.getLecturer(request.lecturerId)
        val group: Group = groupService.getGroupById(request.groupId)

        val auditoryOccupation = auditoryOccupationService.occupyAuditory(
                auditory, timeBlock,
                request.day, request.dateFrom, request.dateTo, request.interval,
                lecturer, Collections.singletonList(group))

        val scheduleItem = ScheduleItem(auditoryOccupation, discipline, request.activityType, schedule)
        scheduleItemRepository.save(scheduleItem)

        return toScheduleItemInfo(scheduleItem)
    }

    fun getScheduleItemById(itemId: Int): ScheduleItem {
        return scheduleItemRepository
                .findById(itemId)
                .orElseThrow { NotFoundException(ErrorCode.SCHEDULE_ITEM_NOT_EXISTS, itemId.toString()) }
    }

    fun getScheduleItemInfo(itemId: Int): ScheduleItemInfo {
        return toScheduleItemInfo(getScheduleItemById(itemId))
    }

    fun getScheduleItemsByLecturer(lecturerId: Int): ScheduleForLecturer {
        val lecturer = lecturerService.getLecturer(lecturerId);
        val scheduleItems = scheduleItemRepository.findByLecturer(lecturerId)

        println(scheduleItems)
        println(toScheduleForLecturer(lecturer, scheduleItems).toString())
        return toScheduleForLecturer(lecturer, scheduleItems)
    }

    fun toScheduleItemInfo(scheduleItem: ScheduleItem): ScheduleItemInfo {
        return ScheduleItemInfo(scheduleItem.id,
                toOccupationInfo(scheduleItem.auditoryOccupation),
                scheduleItem.discipline.name,
                scheduleItem.activityType.description)
    }

    fun toScheduleItemInfoForLecturer(scheduleItem: ScheduleItem): ScheduleItemInfoForLecturer {
        return ScheduleItemInfoForLecturer(
                scheduleItem.auditoryOccupation.day.name,
                scheduleItem.auditoryOccupation.timeBlock.timeFrom,
                scheduleItem.auditoryOccupation.timeBlock.timeTo,
                scheduleItem.auditoryOccupation.dateFrom,
                scheduleItem.auditoryOccupation.dateTo,
                scheduleItem.auditoryOccupation.interval.description,
                scheduleItem.auditoryOccupation.auditory.building.number,
                scheduleItem.auditoryOccupation.auditory.number,
                scheduleItem.auditoryOccupation.groups!!.asSequence().map { it.name }.toList(),
                scheduleItem.discipline.name,
                scheduleItem.activityType.description,
                scheduleItem.auditoryOccupation.comment!!)
    }

    fun toScheduleForLecturer(lecturer: Lecturer, scheduleItems: List<ScheduleItem>): ScheduleForLecturer {
        return ScheduleForLecturer(toLecturerInfo(lecturer),
                scheduleItems.asSequence().map { toScheduleItemInfoForLecturer(it) }.toList())
    }
}