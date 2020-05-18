package omsu.imit.schedule.services

import omsu.imit.schedule.dto.request.CreateEventPeriodRequest
import omsu.imit.schedule.dto.request.CreateScheduleItemRequest
import omsu.imit.schedule.dto.response.ScheduleItemInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Discipline
import omsu.imit.schedule.model.Group
import omsu.imit.schedule.model.Schedule
import omsu.imit.schedule.model.ScheduleItem
import omsu.imit.schedule.repository.ScheduleItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class ScheduleItemService
@Autowired
constructor(private val eventService: EventService,
            private val disciplineService: DisciplineService,
            private val groupService: GroupService,
            private val scheduleService: ScheduleService,
            private val scheduleItemRepository: ScheduleItemRepository) : BaseService() {

    fun createScheduleItem(scheduleId: Int, request: CreateScheduleItemRequest): MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>> {
        val schedule: Schedule = scheduleService.getScheduleById(scheduleId)
        val discipline: Discipline = disciplineService.getDisciplineById(request.disciplineId)
        val groups: List<Group> = groupService.getGroupsByIds(request.groupIds)

        validatePeriodsDates(schedule, request.event.periods)
        val event = eventService.createEvent(request.event)

        val scheduleItem = ScheduleItem(event, discipline, request.activityType, groups, schedule)
        scheduleItemRepository.save(scheduleItem)

        return toScheduleItemFullInfo(scheduleItem)
    }

    fun getScheduleItemById(itemId: Int): ScheduleItem {
        return scheduleItemRepository
                .findById(itemId)
                .orElseThrow { NotFoundException(ErrorCode.SCHEDULE_ITEM_NOT_EXISTS, itemId.toString()) }
    }

    fun getScheduleItemInfo(itemId: Int): MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>> {
        return toScheduleItemFullInfo(getScheduleItemById(itemId))
    }

    private fun toScheduleItemFullInfo(scheduleItem: ScheduleItem): MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>> {
        val scheduleItemsInfo: MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>> = mutableMapOf();

        scheduleItem.event.eventPeriods.asSequence().forEach { eventPeriod ->
            val day = eventPeriod.day.description
            val time = eventPeriod.timeBlock.timeFrom

            val scheduleItemsByDay = scheduleItemsInfo.getOrDefault(day, mutableMapOf())
            val scheduleItemsByTime = scheduleItemsByDay.getOrDefault(time, mutableListOf())

            scheduleItemsByTime.add(toScheduleItemInfo(scheduleItem, eventPeriod))
            scheduleItemsByDay[time] = scheduleItemsByTime
            scheduleItemsInfo[day] = scheduleItemsByDay
        }
        return scheduleItemsInfo
    }

    private fun validatePeriodsDates(schedule: Schedule, periods: List<CreateEventPeriodRequest>) {
        val startYear = schedule.studyYear.split("/")[0]
        val finishYear = schedule.studyYear.split("/")[1]
        val semester = schedule.semester % 2 // 1( 09-01) or 2(02-06)

        val firstSemesterMonths = listOf(9, 10, 11, 12, 1)
        val secondSemesterMonths = listOf(2, 3, 4, 5, 6, 7)
        val semesterMonths = listOf(secondSemesterMonths, firstSemesterMonths)

        periods.asSequence().forEach { period ->
            val dateFrom = LocalDate.parse(period.dateFrom.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            val dateTo = LocalDate.parse(period.dateTo.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            if (dateFrom.year < startYear.toInt() ||
                    dateTo.year > finishYear.toInt() ||
                    !semesterMonths[semester].containsAll<Number>(listOf(dateFrom.month.value, dateTo.month.value))) {
                throw CommonValidationException(ErrorCode.BAD_REQUEST,
                        "The dates of one of the periods don't correspond to the semester of the schedule")
            }
        }
    }
}