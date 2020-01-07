package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateActivityTypeRequest
import omsu.imit.schedule.service.ActivityTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/activity_type")
class ActivityTypeController
@Autowired
constructor(private val activityTypeService: ActivityTypeService) {

    @PostMapping
    fun createActivityType(@RequestBody @Valid request: CreateActivityTypeRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(activityTypeService.createActivityType(request))
    }

    @GetMapping(value = ["/{activityTypeId}"])
    fun getActivityType(@PathVariable activityTypeId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(activityTypeService.getActivityTypeById(activityTypeId))
    }

    @GetMapping
    fun getAllActivityTypes(): ResponseEntity<*> {
        return ResponseEntity.ok().body(activityTypeService.getAllActivityTypes())
    }

    @DeleteMapping(value = ["/{activityTypeId}"])
    fun deleteActivityType(@PathVariable activityTypeId: Int): ResponseEntity<*> {
        activityTypeService.deleteActivityType(activityTypeId)
        return ResponseEntity.noContent().build<Any>()
    }
}