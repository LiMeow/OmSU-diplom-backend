package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.CreateTagRequest
import omsu.imit.schedule.service.TagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/auditories/tags")
class TagController
@Autowired
constructor(private val tagService: TagService) {

    @PostMapping
    fun addTag(@Valid @RequestBody request: CreateTagRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(tagService.addTag(request))
    }

    @GetMapping(value = ["/{id}"])
    fun getTag(@PathVariable("id") tagId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(tagService.getTagById(tagId))
    }

    @GetMapping
    fun getAllTags(): ResponseEntity<*> {
        return ResponseEntity.ok().body(tagService.getAllTags())
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteTag(@PathVariable("id") tagId: Int): ResponseEntity<*> {
        tagService.deleteTagById(tagId)
        return ResponseEntity.noContent().build<Any>()
    }
}
