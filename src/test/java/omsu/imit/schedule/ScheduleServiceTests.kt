package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateScheduleRequest
import omsu.imit.schedule.dto.response.ScheduleInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.repository.ScheduleItemRepository
import omsu.imit.schedule.repository.ScheduleRepository
import omsu.imit.schedule.services.CourseService
import omsu.imit.schedule.services.GroupService
import omsu.imit.schedule.services.LecturerService
import omsu.imit.schedule.services.ScheduleService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.dao.DataIntegrityViolationException
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class ScheduleServiceTests : BaseTests() {
    @MockK
    lateinit var courseService: CourseService

    @MockK
    lateinit var groupService: GroupService

    @MockK
    lateinit var lecturerService: LecturerService

    @MockK
    lateinit var scheduleRepository: ScheduleRepository

    @MockK
    lateinit var scheduleItemRepository: ScheduleItemRepository

    private lateinit var scheduleService: ScheduleService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.scheduleService = ScheduleService(
                this.courseService,
                this.groupService,
                this.lecturerService,
                this.scheduleRepository,
                this.scheduleItemRepository)
    }

    @Test
    fun createSchedule() {
        val schedule = getSchedule()
        val course = getCourse()

        val response = ScheduleInfo(schedule.id, getCourseInfo(course), schedule.semester, schedule.studyYear)
        val request = CreateScheduleRequest(course.id, schedule.semester, schedule.studyYear)

        every { courseService.getCourseById(course.id) } returns course
        every { scheduleRepository.save(schedule) } returns schedule

        assertEquals(response, scheduleService.createSchedule(request))

        verify { courseService.getCourseById(course.id) }
        verify { scheduleRepository.save(schedule) }
    }

    @Test
    fun testCreateAlreadyExistingSchedule() {
        val schedule = getSchedule()
        val course = getCourse()

        val request = CreateScheduleRequest(course.id, schedule.semester, schedule.studyYear)

        every { courseService.getCourseById(course.id) } returns course
        every { scheduleRepository.save(schedule) } throws DataIntegrityViolationException("")

        assertThrows(CommonValidationException::class.java) { scheduleService.createSchedule(request) }

        verify { courseService.getCourseById(course.id) }
        verify { scheduleRepository.save(schedule) }
    }

    @Test
    fun testGetScheduleById() {
        val schedule = getSchedule()

        every { scheduleRepository.findById(schedule.id) } returns Optional.of(schedule)
        assertEquals(schedule, scheduleService.getScheduleById(schedule.id))
        verify { scheduleRepository.findById(schedule.id) }
    }

    @Test
    fun testGetScheduleByNonExistingId() {
        val id = 1

        every { scheduleRepository.findById(id) } returns Optional.empty()
        assertThrows(NotFoundException::class.java) { scheduleService.getScheduleById(id) }
        verify { scheduleRepository.findById(id) }
    }

    @Test
    fun testGetScheduleByGroup() {
        val group = getGroup()
        val scheduleItem = getScheduleItem()
        val studyYear = scheduleItem.schedule.studyYear
        val semester = scheduleItem.schedule.semester


        every { groupService.getGroupById(group.id) } returns group
        every { scheduleItemRepository.findByGroup(group.id, studyYear, semester) } returns listOf(scheduleItem)

        scheduleService.getScheduleByGroup(group.id, studyYear, semester)

        verify { groupService.getGroupById(group.id) }
        verify { scheduleItemRepository.findByGroup(group.id, studyYear, semester) }
    }

    @Test
    fun testGetScheduleByLecturer() {
        val lecturer = getLecturer()
        val scheduleItem = getScheduleItem()
        val studyYear = scheduleItem.schedule.studyYear
        val semester = scheduleItem.schedule.semester


        every { lecturerService.getLecturer(lecturer.id) } returns lecturer
        every { scheduleItemRepository.findByLecturer(lecturer.id, studyYear, semester) } returns listOf(scheduleItem)

        scheduleService.getScheduleByLecturer(lecturer.id, studyYear, semester)

        verify { lecturerService.getLecturer(lecturer.id) }
        verify { scheduleItemRepository.findByLecturer(lecturer.id, studyYear, semester) }
    }

    @Test
    fun testGetScheduleByCourse() {
        val course = getCourse()
        val groups = listOf(getGroup())
        val scheduleItem = getScheduleItem()
        val studyYear = scheduleItem.schedule.studyYear
        val semester = scheduleItem.schedule.semester
        val schedule = getSchedule()

        course.groups = groups

        every { courseService.getCourseById(course.id) } returns course
        every { scheduleItemRepository.findByGroup(course.id, studyYear, semester) } returns listOf(scheduleItem)
        every { scheduleRepository.findByCourse(course.id, studyYear, semester) } returns schedule

        scheduleService.getScheduleByCourse(course.id, studyYear, semester)

        verify { courseService.getCourseById(course.id) }
        verify { scheduleItemRepository.findByGroup(course.id, studyYear, semester) }
    }
}