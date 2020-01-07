package omsu.imit.schedule.controller

import omsu.imit.schedule.service.ChairService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chairs")
class ChairController @Autowired
constructor(private val chairService: ChairService) {

    @GetMapping(value = ["/{chairId}"])
    fun getChair(@PathVariable chairId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(chairService.getChair(chairId))
    }

    @DeleteMapping(value = ["/{chairId}"])
    fun deleteChair(@PathVariable chairId: Int): ResponseEntity<*> {
        chairService.deleteChair(chairId)
        return ResponseEntity.noContent().build<Any>()
    }
}