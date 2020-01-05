package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateScheduleRequest
import omsu.imit.schedule.service.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/schedules")
class ScheduleController
@Autowired
constructor(private val scheduleService: ScheduleService) {

    /**
     * Create schedule
     */
    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createSchedule(@Valid @RequestBody request: CreateScheduleRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(scheduleService.createSchedule(request))
    }

    /**
     * Get schedule by id
     */
    @GetMapping(
            value = ["/{scheduleId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getScheduleById(@PathVariable("scheduleId") scheduleId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(scheduleService.getSchedule(scheduleId))
    }

}