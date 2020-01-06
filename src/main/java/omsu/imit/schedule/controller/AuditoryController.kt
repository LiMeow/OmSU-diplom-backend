package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.CreateAuditoryRequest
import omsu.imit.schedule.requests.EditAuditoryRequest
import omsu.imit.schedule.service.AuditoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
@RequestMapping("/api/auditories")
class AuditoryController
@Autowired
constructor(private val auditoryService: AuditoryService) {

    /**
     * Create auditory
     */
    @PostMapping
    fun createAuditory(@Valid @RequestBody request: CreateAuditoryRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(auditoryService.addAuditory(request))
    }

    /**
     * Return auditory by id
     */
    @GetMapping(value = ["/{auditoryId}"])
    fun getAuditory(@PathVariable("auditoryId") auditoryId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(auditoryService.getAuditoryById(auditoryId))
    }

    /**
     * Update auditory by id
     */
    @PutMapping(value = ["/{auditoryId}"])
    fun editAuditory(@PathVariable("auditoryId") auditoryId: Int,
                     @RequestBody request: EditAuditoryRequest): ResponseEntity<*> {

        return ResponseEntity.ok().body(auditoryService.editAuditory(auditoryId, request))
    }

    /**
     * Delete auditory by id
     */
    @DeleteMapping(value = ["/{auditoryId}"])
    fun deleteAuditory(@PathVariable("auditoryId") auditoryId: Int): ResponseEntity<*> {
        auditoryService.deleteAuditory(auditoryId)
        return ResponseEntity.noContent().build<Any>()
    }
}
