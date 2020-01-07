package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateScheduleRequest
import omsu.imit.schedule.service.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/schedules")
class ScheduleController
@Autowired
constructor(private val scheduleService: ScheduleService) {

    /**
     * Create schedule
     */
    @PostMapping
    fun createSchedule(@RequestBody @Validated request: CreateScheduleRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(scheduleService.createSchedule(request))
    }

    /**
     * Get schedule by id
     */
    @GetMapping(value = ["/{scheduleId}"])
    fun getScheduleById(@PathVariable scheduleId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(scheduleService.getSchedule(scheduleId))
    }

}