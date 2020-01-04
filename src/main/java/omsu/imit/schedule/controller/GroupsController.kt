package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.CreateGroupRequest
import omsu.imit.schedule.service.GroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
@RequestMapping("/api/groups")
class GroupsController @Autowired
constructor(private val groupService: GroupService) {

    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addGroup(@Valid @RequestBody request: CreateGroupRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(groupService.addGroup(request))
    }

    @GetMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getGroup(@PathVariable("id") groupId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(groupService.getGroupById(groupId))
    }

    @GetMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllGroups(): ResponseEntity<*> {

        return ResponseEntity.ok().body(groupService.getAllGroups())
    }


    @DeleteMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteGroup(@PathVariable("id") groupId: Int): ResponseEntity<*> {
        groupService.deleteGroupById(groupId)
        return ResponseEntity.noContent().build<Any>()
    }
}
