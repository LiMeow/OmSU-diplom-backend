package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateEventRequest
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/events")
class EventController
@Autowired
constructor(private val eventService: EventService) {

    @PostMapping()
    fun createEvent(@Valid @RequestBody request: CreateEventRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(eventService.createEventAndGetInfo(request))
    }

    @GetMapping(value = ["/{eventId}"])
    fun getEventById(@PathVariable eventId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(eventService.getEventInfo(eventId))
    }

    @GetMapping(value = ["/{eventId}/classrooms"])
    fun getClassroomsByEvent(@PathVariable eventId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(eventService.getClassroomsByEvent(eventId))
    }

    @DeleteMapping(value = ["/{eventId}"])
    fun deleteClassroomEvent(@PathVariable eventId: Int): StatusResponse {
        eventService.deleteEvent(eventId)
        return StatusResponse.OK
    }
}
