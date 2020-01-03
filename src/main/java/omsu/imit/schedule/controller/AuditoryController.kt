package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.CreateAuditoryRequest
import omsu.imit.schedule.requests.EditAuditoryRequest
import omsu.imit.schedule.service.AuditoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
@RequestMapping("/auditories")
class AuditoryController
@Autowired
constructor(private val auditoryService: AuditoryService) {


    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addAuditory(@Valid @RequestBody request: CreateAuditoryRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(auditoryService.addAuditory(request))
    }

    @GetMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAuditory(@PathVariable("id") auditoryId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(auditoryService.getAuditoryById(auditoryId))
    }

    @PutMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun editAuditory(@PathVariable("id") auditoryId: Int,
                     @RequestBody request: EditAuditoryRequest): ResponseEntity<*> {

        return ResponseEntity.ok().body(auditoryService.editAuditory(auditoryId, request))
    }

    @DeleteMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteAuditory(@PathVariable("id") auditoryId: Int): ResponseEntity<*> {
        auditoryService.deleteAuditory(auditoryId)
        return ResponseEntity.noContent().build<Any>()
    }
}
