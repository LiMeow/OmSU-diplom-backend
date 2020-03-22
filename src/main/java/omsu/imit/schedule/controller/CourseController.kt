package omsu.imit.schedule.controller

import omsu.imit.schedule.service.CourseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("faculties/")
class CourseController
@Autowired
constructor(private val courseService: CourseService) {

    @PostMapping(value = ["/{facultyId}/courses"])
    fun createCourse(@PathVariable @NotNull facultyId: Int,
                     @RequestParam(required = true, defaultValue = "20--") start_year: String,
                     @RequestParam(required = true, defaultValue = "") finish_year: String): ResponseEntity<*> {
        return ResponseEntity.ok().body(courseService.createCourse(facultyId, start_year, finish_year))
    }

    @GetMapping(value = ["/courses/{courseId}"])
    fun getCourseById(@PathVariable courseId: Int): ResponseEntity<*> {
        return ResponseEntity.ok().body(courseService.getCourseInfo(courseId))
    }
}