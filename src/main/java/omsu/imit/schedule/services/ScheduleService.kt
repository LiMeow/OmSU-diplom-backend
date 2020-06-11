package omsu.imit.schedule.services

import omsu.imit.schedule.dto.request.CreateScheduleRequest
import omsu.imit.schedule.dto.response.*
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Group
import omsu.imit.schedule.model.Lecturer
import omsu.imit.schedule.model.Schedule
import omsu.imit.schedule.model.ScheduleItem
import omsu.imit.schedule.repository.ScheduleItemRepository
import omsu.imit.schedule.repository.ScheduleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ScheduleService
@Autowired
constructor(
        private val courseService: CourseService,
        private val groupService: GroupService,
        private val lecturerService: LecturerService,
        private val scheduleRepository: ScheduleRepository,
        private val scheduleItemRepository: ScheduleItemRepository) : BaseService() {

    fun createSchedule(request: CreateScheduleRequest): ScheduleInfo {
        val course = courseService.getCourseById(request.courseId)
        val schedule = Schedule(course, request.semester, request.studyYear)

        try {
            scheduleRepository.save(schedule);
        } catch (e: DataIntegrityViolationException) {
            throw CommonValidationException(ErrorCode.SCHEDULE_ALREADY_EXISTS)
        }
        return ScheduleInfo(schedule.id, toCourseInfo(schedule.course), schedule.semester, schedule.studyYear)
    }

    fun getScheduleById(scheduleId: Int): Schedule {
        return scheduleRepository.findById(scheduleId).orElseThrow {
            NotFoundException(ErrorCode.SCHEDULE_NOT_EXISTS, scheduleId.toString())
        }
    }

    fun getScheduleByGroup(groupId: Int, studyYear: String, semester: Int): ScheduleInfoByGroup {
        val group: Group = groupService.getGroupById(groupId)
        val scheduleItems = scheduleItemRepository.findByGroup(groupId, studyYear, semester)
        return toScheduleInfoByGroup(group, scheduleItems, studyYear, semester)
    }

    fun getScheduleByLecturer(lecturerId: Int, studyYear: String, semester: Int): ScheduleInfoByLecturer {
        val lecturer = lecturerService.getLecturer(lecturerId)
        val scheduleItems = scheduleItemRepository.findByLecturer(lecturerId, studyYear, semester)
        return toScheduleInfoByLecturer(lecturer, scheduleItems, studyYear, semester)

    }

    fun getScheduleByCourse(courseId: Int, studyYear: String, semester: Int): ScheduleInfoByCourse {
        val schedulesInfo: MutableMap<String, ScheduleByCourseElement> = mutableMapOf()
        val course = courseService.getCourseById(courseId)
        val courseNumber = LocalDate.now().year - course.startYear.toInt()
        val schedule: Schedule = this.scheduleRepository.findByCourse(courseId, studyYear, semester)

        course.groups?.asSequence()?.forEach { group ->
            val scheduleItems = scheduleItemRepository.findByGroup(group.id, studyYear, semester)
            schedulesInfo[group.name] = ScheduleByCourseElement(
                    toGroupInfo(group),
                    toScheduleItemsInfo(scheduleItems, true))
        }
        return ScheduleInfoByCourse(
                schedule.id,
                courseNumber,
                semester,
                studyYear,
                schedulesInfo)
    }

    private fun toScheduleItemsInfo(
            scheduleItems: List<ScheduleItem>,
            forGroup: Boolean = false,
            forLecturer: Boolean = false): MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>> {

        val scheduleItemsInfo: MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>> = mutableMapOf();
        scheduleItems.asSequence().forEach { scheduleItem ->
            scheduleItem.event.eventPeriods.asSequence().forEach { eventPeriod ->
                val day = eventPeriod.day.description
                val time = eventPeriod.timeBlock.timeFrom

                val scheduleItemsByDay = scheduleItemsInfo.getOrDefault(day, mutableMapOf())
                val scheduleItemsByTime = scheduleItemsByDay.getOrDefault(time, mutableListOf())

                scheduleItemsByTime.add(toScheduleItemInfo(scheduleItem, eventPeriod, forGroup, forLecturer))
                scheduleItemsByDay[time] = scheduleItemsByTime
                scheduleItemsInfo[day] = scheduleItemsByDay
            }
        }

        return scheduleItemsInfo
    }

    private fun toScheduleInfoByGroup(group: Group, scheduleItems: List<ScheduleItem>, studyYear: String, semester: Int): ScheduleInfoByGroup {
        val course = LocalDate.now().year - group.course.startYear.toInt()
        return ScheduleInfoByGroup(
                course,
                semester,
                studyYear,
                toGroupInfo(group),
                toScheduleItemsInfo(scheduleItems, true))
    }

    private fun toScheduleInfoByLecturer(lecturer: Lecturer, scheduleItems: List<ScheduleItem>, studyYear: String, semester: Int): ScheduleInfoByLecturer {
        return ScheduleInfoByLecturer(
                semester % 2,
                studyYear,
                toLecturerInfo(lecturer),
                toScheduleItemsInfo(scheduleItems, forGroup = false, forLecturer = true))
    }
}