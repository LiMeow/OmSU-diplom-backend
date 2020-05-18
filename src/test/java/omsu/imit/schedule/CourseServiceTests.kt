package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.repository.CourseRepository
import omsu.imit.schedule.services.CourseService
import omsu.imit.schedule.services.FacultyService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.dao.DataIntegrityViolationException
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class CourseServiceTests : BaseTests() {
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

    @Test
    fun testCreateCourse() {
        val faculty = getFaculty()
        val course = getCourse()

        val response = getCourseInfo(course)

        every { facultyService.getFacultyById(faculty.id) } returns faculty
        every { courseRepository.save(course) } returns course

        assertEquals(response, courseService.createCourse(faculty.id, course.startYear.toString(), course.finishYear.toString()))

        verify { facultyService.getFacultyById(faculty.id) }
        verify { courseRepository.save(course) }
    }

    @Test
    fun testCreateAlreadyExistingCourse() {
        val faculty = getFaculty()
        val course = getCourse()

        every { facultyService.getFacultyById(faculty.id) } returns faculty
        every { courseRepository.save(course) } throws DataIntegrityViolationException("")

        Assertions.assertThrows(CommonValidationException::class.java) {
            courseService.createCourse(faculty.id, course.startYear, course.finishYear)
        }

        verify { facultyService.getFacultyById(faculty.id) }
        verify { courseRepository.save(course) }
    }

    @Test
    fun testGetCourseById() {
        val course = getCourse()

        every { courseRepository.findById(course.id) } returns Optional.of(course)

        assertEquals(course, courseService.getCourseById(course.id))
        verify { courseRepository.findById(course.id) }
    }

    @Test
    fun testGetCourseByNonExistingId() {
        val id = 1

        every { courseRepository.findById(id) } returns Optional.empty()

        Assertions.assertThrows(NotFoundException::class.java) { courseService.getCourseById(id) }
        verify { courseRepository.findById(id) }
    }

    @Test
    fun testGetCourseInfo() {
        val course = getCourse()
        val response = getCourseInfo(course)

        every { courseRepository.findById(course.id) } returns Optional.of(course)

        assertEquals(response, courseService.getCourseInfo(course.id))
        verify { courseRepository.findById(course.id) }
    }
}