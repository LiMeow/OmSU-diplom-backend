package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateLecturerRequest
import omsu.imit.schedule.service.LecturerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/lecturers")
class LecturerController
@Autowired
constructor(private val lecturerService: LecturerService) {

    @PostMapping
    fun createLecturer(@Valid @RequestBody request: CreateLecturerRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(lecturerService.createLecturer(request))
    }

    @GetMapping(value = ["/{id}"])
    fun getLecturer(@PathVariable("id") lectureId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(lecturerService.getLecturer(lectureId))
    }

    @GetMapping
    fun getAllLecturers(): ResponseEntity<*> {

        return ResponseEntity.ok().body(lecturerService.getAllLecturers())
    }

    /*@PutMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun editLecturer(@PathVariable("id") lectureId: Int,
                     @Valid @RequestBody request: EditLecturerRequest): ResponseEntity<*> {

        return ResponseEntity.ok().body(lecturerService.editLecturer(lectureId, request))
    }*/

    @DeleteMapping(value = ["/{id}"])
    fun deleteLecturer(@PathVariable("id") lectureId: Int): ResponseEntity<*> {
        lecturerService.deleteLecturer(lectureId)
        return ResponseEntity.noContent().build<Any>()
    }

}
