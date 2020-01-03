package omsu.imit.schedule.controller

import omsu.imit.schedule.service.ChairService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chairs")
class ChairController @Autowired
constructor(private val chairService: ChairService) {

    @GetMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getChair(@PathVariable("id") chairId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(chairService.getChair(chairId))
    }

    @DeleteMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteChair(@PathVariable("id") chairId: Int): ResponseEntity<*> {
        chairService.deleteChair(chairId)
        return ResponseEntity.noContent().build<Any>()
    }
}