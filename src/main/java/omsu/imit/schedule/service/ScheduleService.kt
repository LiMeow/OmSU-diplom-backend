package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateScheduleRequest
import omsu.imit.schedule.dto.response.ScheduleInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Schedule
import omsu.imit.schedule.repository.GroupRepository
import omsu.imit.schedule.repository.ScheduleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class ScheduleService
@Autowired
constructor(private val scheduleRepository: ScheduleRepository,
            private val groupRepository: GroupRepository) : BaseService() {

    fun createSchedule(request: CreateScheduleRequest): ScheduleInfo {
        val group = groupRepository.findById(request.groupId)
                .orElseThrow { NotFoundException(ErrorCode.GROUP_NOT_EXISTS, request.groupId.toString()) }

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

    fun getSchedulesByStudyYearAndSemester(studyYear: String, semester: Int): List<ScheduleInfo> {
        return scheduleRepository
                .findByStudyYearAndSemester(studyYear, semester)
                .asSequence().map { toScheduleInfo(it) }.toList()
    }

    fun getSchedulesByGroup(groupId: Int): List<ScheduleInfo> {
        return scheduleRepository
                .findByGroup(groupId)
                .asSequence().map { toScheduleInfo(it) }.toList()
    }

    fun getScheduleInfo(scheduleId: Int): ScheduleInfo {
        return toScheduleInfo(getScheduleById(scheduleId))
    }

    fun toScheduleInfo(schedule: Schedule): ScheduleInfo {
        return ScheduleInfo(
                schedule.id,
                schedule.course,
                schedule.semester,
                schedule.studyYear,
                toGroupInfo(schedule.group))
    }
}