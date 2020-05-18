package omsu.imit.schedule.controller

import omsu.imit.schedule.services.CourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("/faculties")
@Validated
class CourseController
@Autowired
constructor(private val courseService: CourseService) {

    @PostMapping(value = ["/{facultyId}/courses"])
    fun createCourse(@PathVariable @NotNull facultyId: Int,
                     @RequestParam(required = true, defaultValue = "20--")
                     @Pattern(regexp = "^2\\d{3}", message = "Start year must have format 2***")
                     start_year: String,
                     @RequestParam(required = true, defaultValue = "20--")
                     @Pattern(regexp = "^\\s?|2\\d{3}\$", message = "Finish year must have format 2*** or be blank.")
                     finish_year: String): ResponseEntity<*> {
        return ResponseEntity.ok().body(courseService.createCourse(facultyId, start_year, finish_year))
    }

    @GetMapping(value = ["/courses/{courseId}"])
    fun getCourseById(@PathVariable courseId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(courseService.getCourseInfo(courseId))
    }
}