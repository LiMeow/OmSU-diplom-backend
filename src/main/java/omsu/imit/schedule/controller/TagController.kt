package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateTagRequest
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.model.Tag
import omsu.imit.schedule.services.TagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/classrooms/tags")
class TagController
@Autowired
constructor(private val tagService: TagService) {

    @PostMapping
    fun addTag(@Valid @RequestBody request: CreateTagRequest): ResponseEntity<Tag> {
        return ResponseEntity.ok().body(tagService.createTag(request))
    }

    @GetMapping(value = ["/{tagId}"])
    fun getTag(@PathVariable tagId: Int): ResponseEntity<Tag> {

        return ResponseEntity.ok().body(tagService.getTagById(tagId))
    }

    @GetMapping
    fun getAllTags(): ResponseEntity<MutableList<Tag>> {
        return ResponseEntity.ok().body(tagService.getAllTags())
    }

    @DeleteMapping(value = ["/{tagId}"])
    fun deleteTag(@PathVariable tagId: Int): StatusResponse {
        tagService.deleteTagById(tagId)
        return StatusResponse.OK
    }
}
