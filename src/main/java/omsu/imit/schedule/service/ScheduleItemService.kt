package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateScheduleItemRequest
import omsu.imit.schedule.dto.response.ScheduleItemInfo
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
constructor(private val classroomService: ClassroomService,
            private val eventService: EventService,
            private val disciplineService: DisciplineService,
            private val groupService: GroupService,
            private val lecturerService: LecturerService,
            private val timeBlockService: TimeBlockService,
            private val scheduleService: ScheduleService,
            private val scheduleItemRepository: ScheduleItemRepository) : BaseService() {

    fun createScheduleItem(scheduleId: Int, request: CreateScheduleItemRequest): MutableMap<String, MutableMap<String, ScheduleItemInfo>> {
        val schedule: Schedule = scheduleService.getScheduleById(scheduleId)
        val timeBlock: TimeBlock = timeBlockService.getTimeBlockById(request.timeBlockId)
        val classroom: Classroom = classroomService.getClassroomById(request.classroomId)
        val discipline: Discipline = disciplineService.getDiscipline(request.disciplineId)
        val lecturer: Lecturer = lecturerService.getLecturer(request.lecturerId)
        val group: Group = groupService.getGroupById(request.groupId)

        val event = eventService.createEvent(
                classroom, timeBlock,
                request.day, request.dateFrom, request.dateTo, request.interval,
                lecturer, Collections.singletonList(group))

        val scheduleItem = ScheduleItem(event, discipline, request.activityType, schedule)
        scheduleItemRepository.save(scheduleItem)

        return toScheduleItemFullInfo(scheduleItem)
    }

    fun getScheduleItemById(itemId: Int): ScheduleItem {
        return scheduleItemRepository
                .findById(itemId)
                .orElseThrow { NotFoundException(ErrorCode.SCHEDULE_ITEM_NOT_EXISTS, itemId.toString()) }
    }

    fun getScheduleItemInfo(itemId: Int): ScheduleItemInfo {
        return toScheduleItemInfo(getScheduleItemById(itemId))
    }

    private fun toScheduleItemFullInfo(scheduleItem: ScheduleItem): MutableMap<String, MutableMap<String, ScheduleItemInfo>> {
        val scheduleItemInfo: MutableMap<String, MutableMap<String, ScheduleItemInfo>> = mutableMapOf();
        val day = scheduleItem.event.day.description
        val time = scheduleItem.event.timeBlock.timeFrom

        scheduleItemInfo[day] = mutableMapOf(Pair(time, toScheduleItemInfo(scheduleItem)));
        return scheduleItemInfo
    }
}