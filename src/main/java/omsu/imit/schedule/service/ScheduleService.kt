package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateScheduleRequest
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Schedule
import omsu.imit.schedule.repository.GroupRepository
import omsu.imit.schedule.repository.ScheduleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScheduleService
@Autowired
constructor(private val scheduleRepository: ScheduleRepository,
            private val groupRepository: GroupRepository) {

    fun createSchedule(request: CreateScheduleRequest): Schedule {
        val groups = groupRepository.findAllById(request.groupIds)
                ?: throw NotFoundException(ErrorCode.GROUP_NOT_EXISTS, request.groupIds.toString())

        val schedule = Schedule(request.course, request.semester, request.studyYear, groups)
        scheduleRepository.save(schedule);
        return schedule
    }

    fun getSchedule(scheduleId: Int): Schedule {
        return scheduleRepository.findById(scheduleId).orElseThrow {
            NotFoundException(ErrorCode.SCHEDULE_NOT_EXISTS, scheduleId.toString())
        }
    }

    fun getSchedulesByStudyYear(): List<Schedule> {
        TODO()
    }

    fun getSchedulesBySemester(): List<Schedule> {
        TODO()
    }

    fun getSchedulesByGroup(): List<Schedule> {
        TODO()
    }
}