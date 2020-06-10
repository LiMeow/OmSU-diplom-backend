package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Course
import omsu.imit.schedule.model.Faculty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CourseRepository : JpaRepository<Course, Int> {

    fun findByFaculty(faculty: Faculty): List<Course>
}