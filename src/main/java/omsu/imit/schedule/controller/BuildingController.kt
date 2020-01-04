package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.CreateBuildingRequest
import omsu.imit.schedule.requests.EditBuildingRequest
import omsu.imit.schedule.service.AuditoryService
import omsu.imit.schedule.service.BuildingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/buildings")
class BuildingController @Autowired
constructor(private val auditoryService: AuditoryService,
            private val buildingService: BuildingService) {

    /**
     * Create building
     */
    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addBuilding(@Validated @RequestBody request: CreateBuildingRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(buildingService.addBuilding(request))
    }

    /**
     * Return building by id
     */
    @GetMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getBuilding(@PathVariable("id") buildingId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(buildingService.getBuildingById(buildingId))
    }

    /**
     * Return all auditories by buildings
     */
    @GetMapping(
            value = ["/{id}/auditories"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAuditoriesByBuilding(@PathVariable("id") buildingId: Int,
                                @RequestParam(required = false, defaultValue = "0") page: Int,
                                @RequestParam(required = false, defaultValue = "8") size: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(auditoryService.getAllAuditoriesByBuilding(buildingId, page, size))
    }

    /**
     * Edit building information
     */
    @PutMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun editBuilding(@PathVariable("id") buildingId: Int,
                     @RequestBody request: EditBuildingRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(buildingService.editBuilding(buildingId, request))
    }

    /**
     * Delete building by id
     */
    @DeleteMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteBuilding(@PathVariable("id") buildingId: Int): ResponseEntity<*> {
        buildingService.deleteBuilding(buildingId)
        return ResponseEntity.noContent().build<Any>()
    }

}
