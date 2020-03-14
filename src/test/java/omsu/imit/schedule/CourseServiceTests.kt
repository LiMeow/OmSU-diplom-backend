package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import omsu.imit.schedule.repository.CourseRepository
import omsu.imit.schedule.service.CourseService
import omsu.imit.schedule.service.FacultyService
import org.junit.Before
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CourseServiceTests {
    @MockK
    lateinit var courseRepository: CourseRepository

    @MockK
    lateinit var facultyService: FacultyService

    private lateinit var courseService: CourseService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.courseService = CourseService(this.courseRepository, this.facultyService)
    }
}