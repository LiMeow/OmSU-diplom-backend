package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateGroupRequest
import omsu.imit.schedule.service.GroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/groups")
class GroupsController @Autowired
constructor(private val groupService: GroupService) {

    @PostMapping
    fun createGroup(@Valid @RequestBody request: CreateGroupRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(groupService.createGroup(request))
    }

    @GetMapping(value = ["/{groupId}"])
    fun getGroup(@PathVariable groupId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(groupService.getGroupById(groupId))
    }

    @GetMapping
    fun getAllGroups(): ResponseEntity<*> {

        return ResponseEntity.ok().body(groupService.getAllGroups())
    }


    @DeleteMapping(value = ["/{groupId}"])
    fun deleteGroup(@PathVariable groupId: Int): ResponseEntity<*> {
        groupService.deleteGroupById(groupId)
        return ResponseEntity.noContent().build<Any>()
    }
}
