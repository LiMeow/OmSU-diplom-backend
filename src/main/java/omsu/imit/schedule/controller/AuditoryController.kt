package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateAuditoryRequest
import omsu.imit.schedule.dto.request.EditAuditoryRequest
import omsu.imit.schedule.dto.response.StatusResponse
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

    @PostMapping
    fun createAuditory(@Valid @RequestBody request: CreateAuditoryRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(auditoryService.addAuditory(request))
    }

    @GetMapping(value = ["/{auditoryId}"])
    fun getAuditoryById(@PathVariable auditoryId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(auditoryService.getAuditoryInfo(auditoryId))
    }

    @GetMapping()
    fun getAuditoriesByTags(@RequestParam tags: List<String>): ResponseEntity<*> {
        return ResponseEntity.ok().body(auditoryService.getAuditoriesByTags(tags))
    }

    @PutMapping(value = ["/{auditoryId}"])
    fun editAuditory(@PathVariable auditoryId: Int,
                     @RequestBody request: EditAuditoryRequest): ResponseEntity<*> {

        return ResponseEntity.ok().body(auditoryService.editAuditory(auditoryId, request))
    }

    @DeleteMapping(value = ["/{auditoryId}"])
    fun deleteAuditory(@PathVariable auditoryId: Int): StatusResponse {
        auditoryService.deleteAuditory(auditoryId)
        return StatusResponse.OK
    }
}
