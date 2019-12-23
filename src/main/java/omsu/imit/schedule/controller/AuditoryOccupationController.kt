package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.OccupyAuditoryRequest
import omsu.imit.schedule.service.AuditoryOccupationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auditories")
class AuditoryOccupationController @Autowired
constructor(private val auditoryOccupationService: AuditoryOccupationService) {

    @PostMapping(
            value = ["/{id}/occupations"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun occupyAuditory(@PathVariable("id") auditoryId: Int,
                       @Valid @RequestBody request: OccupyAuditoryRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(auditoryOccupationService.occupyAuditory(auditoryId, request))
    }

    @DeleteMapping(
            value = ["/occupations/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteAuditoryOccupation(@PathVariable("id") occupationId: Int): ResponseEntity<*> {
        auditoryOccupationService.deleteAuditoryOccupation(occupationId)
        return ResponseEntity.noContent().build<Any>()
    }

    @DeleteMapping(
            value = ["/{id}/occupations"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteAllAuditoryOccupations(@PathVariable("id") auditoryId: Int): ResponseEntity<*> {
        auditoryOccupationService.deleteAllAuditoryOccupations(auditoryId)
        return ResponseEntity.noContent().build<Any>()
    }
}
