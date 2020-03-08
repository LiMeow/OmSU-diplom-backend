package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateEventRequest
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.service.EventService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/classrooms")
class EventController
@Autowired
constructor(private val eventService: EventService) {

    @PostMapping(value = ["/{classroomId}/events"])
    fun createEvent(@PathVariable classroomId: Int,
                    @Valid @RequestBody request: CreateEventRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(eventService.createEvent(classroomId, request))
    }

//    /**
//     * Return auditory with occupations by date
//     */
//    @GetMapping(value = ["/{auditoryId}/occupations"])
//    fun getAuditoryWithOccupationsByDate(@PathVariable auditoryId: Int,
//                                         @RequestParam date: String): ResponseEntity<*> {
//
//        return ResponseEntity.ok().body(auditoryOccupationService.getAuditoryWithOccupationsByDate(auditoryId, date))
//    }

    @GetMapping(value = ["/events/{eventId}"])
    fun getEventById(@PathVariable eventId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(eventService.getEventInfo(eventId))
    }

    @DeleteMapping(value = ["/events/{eventId}"])
    fun deleteClassroomEvent(@PathVariable eventId: Int): StatusResponse {
        eventService.deleteEvent(eventId)
        return StatusResponse.OK
    }

    @DeleteMapping(value = ["/{classroomId}/events"])
    fun deleteAllClassroomEvents(@PathVariable classroomId: Int): StatusResponse {
        eventService.deleteAllEvents(classroomId)
        return StatusResponse.OK
    }
}
