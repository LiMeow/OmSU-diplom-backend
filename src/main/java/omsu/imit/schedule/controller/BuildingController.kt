package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateBuildingRequest
import omsu.imit.schedule.dto.request.EditBuildingRequest
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

    @PostMapping
    fun addBuilding(@Valid @RequestBody request: CreateBuildingRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(buildingService.createBuilding(request))
    }

    @GetMapping(value = ["/{buildingId}"])
    fun getBuilding(@PathVariable buildingId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(buildingService.getBuildingInfo(buildingId))
    }

    @GetMapping
    fun getAllBuildings(): ResponseEntity<*> {

        return ResponseEntity.ok().body(buildingService.getAllBuildings())
    }

    @GetMapping(value = ["/{buildingId}/auditories"])
    fun getAuditoriesByBuilding(@PathVariable buildingId: Int,
                                @RequestParam(required = false, defaultValue = "0") page: Int,
                                @RequestParam(required = false, defaultValue = Int.MAX_VALUE.toString()) size: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(auditoryService.getAllAuditoriesByBuilding(buildingId, page, size))
    }

    @PutMapping(value = ["/{buildingId}"])
    fun editBuilding(@PathVariable buildingId: Int,
                     @RequestBody request: EditBuildingRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(buildingService.editBuilding(buildingId, request))
    }

    @DeleteMapping(value = ["/{buildingId}"])
    fun deleteBuilding(@PathVariable buildingId: Int): ResponseEntity<*> {
        buildingService.deleteBuilding(buildingId)
        return ResponseEntity.noContent().build<Any>()
    }

}
