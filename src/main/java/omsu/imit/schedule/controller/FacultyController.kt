package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateFacultyRequest
import omsu.imit.schedule.service.FacultyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/faculties")
class FacultyController
@Autowired constructor(private val facultyService: FacultyService) {

    @PostMapping
    fun createFaculty(@Valid @RequestBody request: CreateFacultyRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(facultyService.createFaculty(request))
    }

    @GetMapping(value = ["/{facultyId}"])
    fun getFacultyInfo(@PathVariable facultyId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(facultyService.getFacultyInfo(facultyId))
    }

    @GetMapping
    fun getAllFaculties(): ResponseEntity<*> {

        return ResponseEntity.ok().body(facultyService.getAllFaculties())
    }

    @DeleteMapping(value = ["/{facultyId}"])
    fun deleteFaculty(@PathVariable facultyId: Int): ResponseEntity<*> {
        facultyService.deleteFaculty(facultyId)
        return ResponseEntity.noContent().build<Any>()
    }
}