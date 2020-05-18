package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateLecturerRequest
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.services.LecturerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/lecturers")
class LecturerController
@Autowired
constructor(private val lecturerService: LecturerService) {

    @PostMapping
    fun createLecturer(@Valid @RequestBody request: CreateLecturerRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(lecturerService.createLecturer(request))
    }

    @GetMapping(value = ["/{lectureId}"])
    fun getLecturer(@PathVariable lectureId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(lecturerService.getLecturerInfo(lectureId))
    }

    @GetMapping
    fun getAllLecturers(): ResponseEntity<*> {
        return ResponseEntity.ok().body(lecturerService.getAllLecturers())
    }

//    @PutMapping(value = ["/{id}"])
//    fun editLecturer(@PathVariable("id") lectureId: Int,
//                     @Valid @RequestBody request: EditLecturerRequest): ResponseEntity<*> {
//
//        return ResponseEntity.ok().body(lecturerService.editLecturer(lectureId, request))
//    }

    @DeleteMapping(value = ["/{lectureId}"])
    fun deleteLecturer(@PathVariable lectureId: Int): StatusResponse {
        lecturerService.deleteLecturer(lectureId)
        return StatusResponse.OK
    }

}
