package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.OccupyAuditoryRequest
import omsu.imit.schedule.service.AuditoryOccupationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/auditories")
class AuditoryOccupationController @Autowired
constructor(private val auditoryOccupationService: AuditoryOccupationService) {

    /**
     * Create auditory occupation
     */
    @PostMapping(
            value = ["/{auditoryId}/occupations"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun occupyAuditory(@PathVariable("auditoryId") auditoryId: Int,
                       @Valid @RequestBody request: OccupyAuditoryRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(auditoryOccupationService.occupyAuditory(auditoryId, request))
    }

    /**
     * Return occupations by auditory id and date
     */
    @GetMapping(
            value = ["/{auditoryId}/occupations"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getOccupationsByAuditoryAndDate(@PathVariable("auditoryId") auditoryId: Int,
                                        @RequestParam date: String): ResponseEntity<*> {

        return ResponseEntity.ok().body(auditoryOccupationService.getOccupationByAuditoryAndDate(auditoryId, date))
    }

    /**
     * Delete occupation by id
     */
    @DeleteMapping(
            value = ["/occupations/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteAuditoryOccupation(@PathVariable("id") occupationId: Int): ResponseEntity<*> {
        auditoryOccupationService.deleteAuditoryOccupation(occupationId)
        return ResponseEntity.noContent().build<Any>()
    }

    /**
     * Delete all occupations by auditory id
     */
    @DeleteMapping(
            value = ["/{auditoryId}/occupations"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteAllAuditoryOccupations(@PathVariable("auditoryId") auditoryId: Int): ResponseEntity<*> {
        auditoryOccupationService.deleteAllAuditoryOccupations(auditoryId)
        return ResponseEntity.noContent().build<Any>()
    }
}
