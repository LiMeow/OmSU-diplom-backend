package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateScheduleRequest
import omsu.imit.schedule.repository.ScheduleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ScheduleService
@Autowired
constructor(private val scheduleRepository: ScheduleRepository) {

    fun createSchedule(request: CreateScheduleRequest): Any {
        if(request.course<1 || request.course>4){

        }
        TODO("not implemented")
    }

    fun getSchedule(scheduleId: Int): Any {
        TODO("not implemented")
    }

    private fun validate() {

    }
}