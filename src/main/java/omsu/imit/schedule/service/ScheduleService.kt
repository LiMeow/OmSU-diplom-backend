package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateScheduleRequest
import omsu.imit.schedule.dto.response.ScheduleInfo
import omsu.imit.schedule.dto.response.ScheduleInfoForLecturer
import omsu.imit.schedule.dto.response.ScheduleItemInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Lecturer
import omsu.imit.schedule.model.Schedule
import omsu.imit.schedule.model.ScheduleItem
import omsu.imit.schedule.repository.ScheduleItemRepository
import omsu.imit.schedule.repository.ScheduleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class ScheduleService
@Autowired
constructor(
        private val groupService: GroupService,
        private val lecturerService: LecturerService,
        private val scheduleRepository: ScheduleRepository,
        private val scheduleItemRepository: ScheduleItemRepository) : BaseService() {

    fun createSchedule(request: CreateScheduleRequest): ScheduleInfo {
        val group = groupService.getGroupById(request.groupId)
        val schedule = Schedule(request.course, request.semester, request.studyYear, group)

        try {
            scheduleRepository.save(schedule);
        } catch (e: DataIntegrityViolationException) {
            throw CommonValidationException(ErrorCode.SCHEDULE_ALREADY_EXISTS)
        }
        return toScheduleInfo(schedule)
    }

    fun getScheduleById(scheduleId: Int): Schedule {
        return scheduleRepository.findById(scheduleId).orElseThrow {
            NotFoundException(ErrorCode.SCHEDULE_NOT_EXISTS, scheduleId.toString())
        }
    }

    fun getScheduleByGroupStudyYearAndSemester(groupId: Int, studyYear: String, semester: Int): ScheduleInfo {
        val schedule = scheduleRepository.findByGroup(groupId, studyYear, semester)
        return toScheduleInfo(schedule)
    }

    fun getScheduleByLecturer(lecturerId: Int, studyYear: String, semester: Int): Any {
        val lecturer = lecturerService.getLecturer(lecturerId)
        val scheduleItems = scheduleItemRepository.findByLecturer(lecturerId, studyYear, semester)
        println(scheduleItems)
        return toScheduleInfoForLecturer(lecturer, scheduleItems, studyYear, semester)

    }

    fun getScheduleInfoById(scheduleId: Int): ScheduleInfo {
        return toScheduleInfo(getScheduleById(scheduleId))
    }

    private fun toScheduleItemsInfo(scheduleItems: List<ScheduleItem>): MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>> {
        val scheduleItemsInfo: MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>> = mutableMapOf();

        scheduleItems.asSequence().forEach {
            val day = it.event.day.description
            val time = it.event.timeBlock.timeFrom

            val scheduleItemsByDay = scheduleItemsInfo.getOrDefault(day, mutableMapOf())
            val scheduleItemsByTime = scheduleItemsByDay.getOrDefault(time, mutableListOf())

            scheduleItemsByTime.add(toScheduleItemInfo(it))
            scheduleItemsByDay[time] = scheduleItemsByTime
            scheduleItemsInfo[day] = scheduleItemsByDay
        }

        return scheduleItemsInfo
    }

    private fun toScheduleInfo(schedule: Schedule): ScheduleInfo {
        return ScheduleInfo(
                schedule.id,
                schedule.course,
                schedule.semester,
                schedule.studyYear,
                toGroupInfo(schedule.group),
                toScheduleItemsInfo(schedule.scheduleItems))
    }

    private fun toScheduleInfoForLecturer(lecturer: Lecturer, scheduleItems: List<ScheduleItem>, studyYear: String, semester: Int): ScheduleInfoForLecturer {
        return ScheduleInfoForLecturer(
                semester % 2,
                studyYear,
                toLecturerInfo(lecturer),
                toScheduleItemsInfo(scheduleItems))
    }
}