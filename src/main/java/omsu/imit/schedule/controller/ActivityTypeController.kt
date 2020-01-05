package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.CreateActivityTypeRequest
import omsu.imit.schedule.service.ActivityTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/activity_type")
class ActivityTypeController
@Autowired
constructor(private val activityTypeService: ActivityTypeService) {

    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createActivityType(@Valid @RequestBody request: CreateActivityTypeRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(activityTypeService.createActivityType(request))
    }

    @GetMapping(
            value = ["/{activityTypeId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getActivityType(@PathVariable("activityTypeId") activityTypeId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(activityTypeService.getActivityTypeById(activityTypeId))
    }

    @GetMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllActivityTypes(): ResponseEntity<*> {
        return ResponseEntity.ok().body(activityTypeService.getAllActivityTypes())
    }

    @DeleteMapping(
            value = ["/{activityTypeId}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteActivityType(@PathVariable("activityTypeId") activityTypeId: Int): ResponseEntity<*> {
        activityTypeService.deleteActivityType(activityTypeId)
        return ResponseEntity.noContent().build<Any>()
    }
}