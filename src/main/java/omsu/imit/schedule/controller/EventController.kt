package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CancelEventRequest
import omsu.imit.schedule.dto.request.CreateEventRequest
import omsu.imit.schedule.dto.request.EditEventRequest
import omsu.imit.schedule.dto.request.RescheduleEventRequest
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.services.EventService
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

    @PutMapping(value = ["/{eventId}"])
    fun editEvent(@PathVariable eventId: Int,
                  @Valid @RequestBody request: EditEventRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(eventService.editEvent(eventId, request))
    }

    @PutMapping(value = ["/reschedule"])
    fun rescheduleEventPeriod(@Valid @RequestBody request: RescheduleEventRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(eventService.rescheduleEventPeriod(request))
    }

    @DeleteMapping(value = ["/cancel"])
    fun cancelEventOnSomeDates(@Valid @RequestBody request: CancelEventRequest): Any {
        val event = eventService.cancelEventOnSomeDates(request)
        return if (event !== null) ResponseEntity.ok().body(event) else StatusResponse.OK
    }

    @DeleteMapping(value = ["/{eventId}"])
    fun deleteEvent(@PathVariable eventId: Int): StatusResponse {
        eventService.deleteEvent(eventId)
        return StatusResponse.OK
    }
}
