package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.CreateFacultyRequest
import omsu.imit.schedule.dto.response.FacultyInfo
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.services.FacultyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/faculties")
class FacultyController
@Autowired constructor(private val facultyService: FacultyService) {

    @PostMapping
    fun createFaculty(@Valid @RequestBody request: CreateFacultyRequest): ResponseEntity<FacultyInfo> {
        return ResponseEntity.ok().body(facultyService.createFaculty(request))
    }

    @GetMapping(value = ["/{facultyId}"])
    fun getFacultyInfo(@PathVariable facultyId: Int): ResponseEntity<FacultyInfo> {

        return ResponseEntity.ok().body(facultyService.getFacultyInfo(facultyId))
    }

    @GetMapping
    fun getAllFaculties(): ResponseEntity<List<FacultyInfo>> {

        return ResponseEntity.ok().body(facultyService.getAllFaculties())
    }

    @DeleteMapping(value = ["/{facultyId}"])
    fun deleteFaculty(@PathVariable facultyId: Int): StatusResponse {
        facultyService.deleteFaculty(facultyId)
        return StatusResponse.OK
    }
}