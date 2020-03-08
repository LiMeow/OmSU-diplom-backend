package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateScheduleRequest
import omsu.imit.schedule.service.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/schedules")
class ScheduleController
@Autowired
constructor(private val scheduleService: ScheduleService) {

    @PostMapping
    fun createSchedule(@RequestBody @Validated request: CreateScheduleRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(scheduleService.createSchedule(request))
    }

    @GetMapping(value = ["/{scheduleId}"])
    fun getScheduleById(@PathVariable scheduleId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(scheduleService.getScheduleInfoById(scheduleId))
    }

    @GetMapping(value = ["/groups/{groupId}"])
    fun getScheduleByGroup(@PathVariable groupId: Int,
                           @RequestParam(required = true, defaultValue = "20--/20--") studyYear: String,
                           @RequestParam(required = true, defaultValue = "1") semester: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(scheduleService.getScheduleByGroupStudyYearAndSemester(groupId, studyYear, semester))
    }

    @GetMapping(value = ["/lecturers/{lecturerId}"])
    fun getScheduleByLecturer(@PathVariable lecturerId: Int,
                              @RequestParam(required = true, defaultValue = "20--/20--") studyYear: String,
                              @RequestParam(required = true, defaultValue = "1") semester: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(scheduleService.getScheduleByLecturer(lecturerId, studyYear, semester))
    }
}