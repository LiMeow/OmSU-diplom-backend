package omsu.imit.schedule.controller

import omsu.imit.schedule.requests.CreateFacultyRequest
import omsu.imit.schedule.service.FacultyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/faculties")
class FacultyController
@Autowired constructor(private val facultyService: FacultyService) {
    /**
     * Create faculty
     */
    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createFaculty(@Valid @RequestBody request: CreateFacultyRequest): ResponseEntity<*> {
        return ResponseEntity.ok().body(facultyService.createFaculty(request))
    }

    /**
     * Return faculty by id
     */
    @GetMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getFacultyInfo(@PathVariable("id") facultyId: Int): ResponseEntity<*> {

        return ResponseEntity.ok().body(facultyService.getFaculty(facultyId))
    }

    /**
     * Return all faculties
     */
    @GetMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllFaculties(): ResponseEntity<*> {

        return ResponseEntity.ok().body(facultyService.getAllFaculties())
    }

    /**
     * Delete faculty by id
     */
    @DeleteMapping(
            value = ["/{id}"],
            produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteFaculty(@PathVariable("id") facultyId: Int): ResponseEntity<*> {
        facultyService.deleteFaculty(facultyId)
        return ResponseEntity.noContent().build<Any>()
    }
}