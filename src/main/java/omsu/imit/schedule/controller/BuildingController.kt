package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.CreateBuildingRequest
import omsu.imit.schedule.requests.EditBuildingRequest
import omsu.imit.schedule.service.AuditoryService
import omsu.imit.schedule.service.BuildingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/buildings")
class BuildingController @Autowired
constructor(private val auditoryService: AuditoryService,
            private val buildingService: BuildingService) {

    /**
     * Create building
     */
    @PostMapping
    fun addBuilding(@Valid @RequestBody request: CreateBuildingRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(buildingService.addBuilding(request))
    }

    /**
     * Return building by id
     */
    @GetMapping(value = ["/{buildingId}"])
    fun getBuilding(@PathVariable("buildingId") buildingId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(buildingService.getBuildingById(buildingId))
    }

    /**
     * Return all auditories by buildings
     */
    @GetMapping(value = ["/{buildingId}/auditories"])
    fun getAuditoriesByBuilding(@PathVariable("buildingId") buildingId: Int,
                                @RequestParam(required = false, defaultValue = "0") page: Int,
                                @RequestParam(required = false, defaultValue = "8") size: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(auditoryService.getAllAuditoriesByBuilding(buildingId, page, size))
    }

    /**
     * Edit building information
     */
    @PutMapping(value = ["/{buildingId}"])
    fun editBuilding(@PathVariable("buildingId") buildingId: Int,
                     @RequestBody request: EditBuildingRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(buildingService.editBuilding(buildingId, request))
    }

    /**
     * Delete building by id
     */
    @DeleteMapping(value = ["/{buildingId}"])
    fun deleteBuilding(@PathVariable("buildingId") buildingId: Int): ResponseEntity<*> {
        buildingService.deleteBuilding(buildingId)
        return ResponseEntity.noContent().build<Any>()
    }

}
