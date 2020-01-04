package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.CreateTagRequest
import omsu.imit.schedule.service.TagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
@RequestMapping("/api/auditories/tags")
class TagController
@Autowired
constructor(private val tagService: TagService) {

    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addTag(@Valid @RequestBody request: CreateTagRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(tagService.addTag(request))
    }

    @GetMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getTag(@PathVariable("id") tagId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(tagService.getTagById(tagId))
    }

    @GetMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllTags(): ResponseEntity<*> {
        return ResponseEntity.ok().body(tagService.getAllTags())
    }

    @DeleteMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteTag(@PathVariable("id") tagId: Int): ResponseEntity<*> {
        tagService.deleteTagById(tagId)
        return ResponseEntity.noContent().build<Any>()
    }
}
