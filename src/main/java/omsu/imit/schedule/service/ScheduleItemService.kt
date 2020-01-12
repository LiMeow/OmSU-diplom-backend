package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateScheduleItemRequest
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
            private val activityTypeService: ActivityTypeService,
            private val disciplineService: DisciplineService,
            private val groupService: GroupService,
            private val lecturerService: LecturerService,
            private val timeBlockService: TimeBlockService,
            private val scheduleService: ScheduleService,
            private val scheduleItemRepository: ScheduleItemRepository) {

    fun createScheduleItem(scheduleId: Int, request: CreateScheduleItemRequest): ScheduleItem {
        val schedule: Schedule = scheduleService.getSchedule(scheduleId)
        val timeBlock: TimeBlock = timeBlockService.getTimeBlockById(request.timeBlockId)
        val auditory: Auditory = auditoryService.getAuditoryById(request.auditoryId)
        val discipline: Discipline = disciplineService.getDiscipline(request.disciplineId)
        val lecturer: Lecturer = lecturerService.getLecturer(request.lecturerId)
        val group: Group = groupService.getGroupById(request.groupId)
        val activityType = activityTypeService.getActivityTypeById(request.activityTypeId)

        val auditoryOccupation = auditoryOccupationService.occupyAuditory(
                auditory, timeBlock,
                request.day, request.dateFrom, request.dateTo, request.interval,
                lecturer, Collections.singletonList(group))

        val scheduleItem = ScheduleItem(auditoryOccupation, discipline, activityType, schedule)
        scheduleItemRepository.save(scheduleItem)
        return scheduleItem
    }

}