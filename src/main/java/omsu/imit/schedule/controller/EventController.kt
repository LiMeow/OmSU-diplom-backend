package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CancelEventRequest
import omsu.imit.schedule.dto.request.CreateEventRequest
import omsu.imit.schedule.dto.request.EditEventRequest
import omsu.imit.schedule.dto.request.RescheduleEventRequest
import omsu.imit.schedule.dto.response.ClassroomShortInfo
import omsu.imit.schedule.dto.response.EventInfo
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
    fun createEvent(@Valid @RequestBody request: CreateEventRequest): ResponseEntity<EventInfo> {
        return ResponseEntity.ok().body(eventService.createEventAndGetInfo(request))
    }

    @GetMapping(value = ["/{eventId}"])
    fun getEventById(@PathVariable eventId: Int): ResponseEntity<EventInfo> {
        return ResponseEntity.ok().body(eventService.getEventInfo(eventId))
    }

    @GetMapping(value = ["/{eventId}/classrooms"])
    fun getClassroomsByEvent(@PathVariable eventId: Int): ResponseEntity<List<ClassroomShortInfo>> {
        return ResponseEntity.ok().body(eventService.getClassroomsByEvent(eventId))
    }

    @PutMapping(value = ["/{eventId}"])
    fun editEvent(@PathVariable eventId: Int,
                  @Valid @RequestBody request: EditEventRequest): ResponseEntity<EventInfo> {
        return ResponseEntity.ok().body(eventService.editEventAndGetInfo(eventId, request))
    }

    @PutMapping(value = ["/reschedule"])
    fun rescheduleEventPeriod(@Valid @RequestBody request: RescheduleEventRequest): ResponseEntity<EventInfo> {
        return ResponseEntity.ok().body(eventService.rescheduleEventPeriod(request))
    }

    @DeleteMapping(value = ["/cancel"])
    fun cancelEventOnSomeDates(@Valid @RequestBody request: CancelEventRequest): ResponseEntity<EventInfo?> {
        val event = eventService.cancelEventOnSomeDates(request)
        return if (event !== null) ResponseEntity.ok().body(event) else ResponseEntity.ok().body(null)
    }

    @DeleteMapping(value = ["/{eventId}"])
    fun deleteEvent(@PathVariable eventId: Int): StatusResponse {
        eventService.deleteEvent(eventId)
        return StatusResponse.OK
    }
}
