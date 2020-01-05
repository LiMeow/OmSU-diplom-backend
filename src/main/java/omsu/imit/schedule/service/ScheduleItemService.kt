package omsu.imit.schedule.service

import omsu.imit.schedule.repository.ScheduleItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
internal class ScheduleItemService
@Autowired
constructor(private  val scheduleItemRepository: ScheduleItemRepository){
}