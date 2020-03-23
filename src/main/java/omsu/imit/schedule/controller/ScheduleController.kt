package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateScheduleRequest
import omsu.imit.schedule.service.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/schedules")
@Validated
class ScheduleController
@Autowired
constructor(private val scheduleService: ScheduleService) {

    @PostMapping
    fun createSchedule(@RequestBody @Validated request: CreateScheduleRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(scheduleService.createSchedule(request))
    }

    @GetMapping(value = ["/groups/{groupId}"])
    fun getScheduleByGroup(@PathVariable groupId: Int,
                           @RequestParam(required = true, defaultValue = "20--/20--")
                           @Pattern(regexp = "^2\\d{3}/2\\d{3}\$", message = "Study year must have format 2***/2***")
                           studyYear: String,
                           @RequestParam(required = true, defaultValue = "1") semester: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(scheduleService.getScheduleByGroup(groupId, studyYear, semester))
    }

    @GetMapping(value = ["/lecturers/{lecturerId}"])
    fun getScheduleByLecturer(@PathVariable lecturerId: Int,
                              @RequestParam(required = true, defaultValue = "20--/20--")
                              @Pattern(regexp = "^2\\d{3}/2\\d{3}\$", message = "Study year must have format 2***/2***")
                              studyYear: String,
                              @RequestParam(required = true, defaultValue = "1") semester: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(scheduleService.getScheduleByLecturer(lecturerId, studyYear, semester))
    }

    @GetMapping(value = ["/courses/{courseId}"])
    fun getScheduleByCourse(@PathVariable courseId: Int,
                            @RequestParam(required = true, defaultValue = "20--/20--")
                            @Pattern(regexp = "^2\\d{3}/2\\d{3}\$", message = "Study year must have format 2***/2***")
                            studyYear: String,
                            @RequestParam(required = true, defaultValue = "1") semester: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(scheduleService.getScheduleByCourse(courseId, studyYear, semester))
    }
}