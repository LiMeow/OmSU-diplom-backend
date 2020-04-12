package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CancelEventRequest
import omsu.imit.schedule.dto.request.CreateEventRequest
import omsu.imit.schedule.dto.request.RescheduleEventRequest
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

    @PutMapping(value = ["/reschedule/{eventId}"])
    fun rescheduleEvent(@PathVariable eventId: Int,
                        @Valid @RequestBody request: RescheduleEventRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(eventService.rescheduleEvent(request, eventId))
    }

    @DeleteMapping(value = ["/cancel/{eventId}"])
    fun cancelEventOnSomeDates(@PathVariable eventId: Int,
                               @Valid @RequestBody request: CancelEventRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(eventService.cancelEventOnSomeDates(request, eventId))
    }

    @DeleteMapping(value = ["/{eventId}"])
    fun deleteEvent(@PathVariable eventId: Int): StatusResponse {
        eventService.deleteEvent(eventId)
        return StatusResponse.OK
    }
}
