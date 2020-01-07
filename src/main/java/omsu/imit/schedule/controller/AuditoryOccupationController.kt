package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.OccupyAuditoryRequest
import omsu.imit.schedule.service.AuditoryOccupationService
import org.springframework.beans.factory.annotation.Autowired
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
    @PostMapping(value = ["/{auditoryId}/occupations"])
    fun occupyAuditory(@PathVariable auditoryId: Int,
                       @Valid @RequestBody request: OccupyAuditoryRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(auditoryOccupationService.occupyAuditory(auditoryId, request))
    }

    /**
     * Return auditory with occupations by date
     */
    @GetMapping(value = ["/{auditoryId}/occupations"])
    fun getAuditoryWithOccupationsByDate(@PathVariable auditoryId: Int,
                                         @RequestParam date: String): ResponseEntity<*> {

        return ResponseEntity.ok().body(auditoryOccupationService.getAuditoryWithOccupationsByDate(auditoryId, date))
    }

    /**
     * Delete occupation by id
     */
    @DeleteMapping(value = ["/occupations/{occupationId}"])
    fun deleteAuditoryOccupation(@PathVariable occupationId: Int): ResponseEntity<*> {
        auditoryOccupationService.deleteAuditoryOccupation(occupationId)
        return ResponseEntity.noContent().build<Any>()
    }

    /**
     * Delete all occupations by auditory id
     */
    @DeleteMapping(value = ["/{auditoryId}/occupations"])
    fun deleteAllAuditoryOccupations(@PathVariable auditoryId: Int): ResponseEntity<*> {
        auditoryOccupationService.deleteAllAuditoryOccupations(auditoryId)
        return ResponseEntity.noContent().build<Any>()
    }
}
