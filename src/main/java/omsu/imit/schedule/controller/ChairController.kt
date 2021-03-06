package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateChairRequest
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.services.ChairService
import omsu.imit.schedule.services.LecturerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/chairs")
class ChairController @Autowired
constructor(private val chairService: ChairService,
            private val lecturerService: LecturerService) {

    @PostMapping
    fun createChair(@Valid @RequestBody request: CreateChairRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(chairService.createChair(request))
    }

    @GetMapping(value = ["/{chairId}"])
    fun getChair(@PathVariable chairId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(chairService.getChairInfo(chairId))
    }

    @GetMapping(value = ["/{chairId}/lecturers"])
    fun getLecturersByChair(@PathVariable chairId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(lecturerService.getLecturersByChair(chairId))
    }

    @GetMapping
    fun getAllChairs(): ResponseEntity<*> {
        return ResponseEntity.ok().body(chairService.getAllChairs())
    }

    @DeleteMapping(value = ["/{chairId}"])
    fun deleteChair(@PathVariable chairId: Int): StatusResponse {
        chairService.deleteChair(chairId)
        return StatusResponse.OK
    }
}