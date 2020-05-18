package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateLecturerRequest
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.repository.LecturerRepository
import omsu.imit.schedule.repository.UserRepository
import omsu.imit.schedule.services.ChairService
import omsu.imit.schedule.services.LecturerService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class LecturerServiceTests : BaseTests() {
    @MockK
    lateinit var chairService: ChairService

    @MockK
    lateinit var lecturerRepository: LecturerRepository

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var lecturerService: LecturerService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.lecturerService = LecturerService(
                this.chairService,
                this.lecturerRepository,
                this.userRepository)
    }


    @Test
    fun testCreateLecturer() {
        val chair = getChair()
        val personalData = getLecturerPersonalData()
        val lecturer = getLecturer()

        val request = CreateLecturerRequest(
                chair.id,
                lecturer.user.firstName,
                lecturer.user.patronymic,
                lecturer.user.lastName,
                lecturer.user.email)

        val response = getLecturerInfo(lecturer)

        every { chairService.getChairById(chair.id) } returns chair
        every { userRepository.save(personalData) } returns personalData
        every { lecturerRepository.save(lecturer) } returns lecturer

        assertEquals(response, lecturerService.createLecturer(request))

        verify { chairService.getChairById(chair.id) }
        verify { userRepository.save(personalData) }
        verify { lecturerRepository.save(lecturer) }
    }

    @Test
    fun testGetLecturerById() {
        val lecturer = getLecturer()

        every { lecturerRepository.findById(lecturer.id) } returns Optional.of(lecturer)

        assertEquals(lecturer, lecturerService.getLecturer(lecturer.id))
        verify { lecturerRepository.findById(lecturer.id) }
    }

    @Test
    fun testGetLecturerByNonExistingId() {
        val id = 1

        every { lecturerRepository.findById(id) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { lecturerService.getLecturer(id) }
        verify { lecturerRepository.findById(id) }
    }

    @Test
    fun testGetAllLecturers() {
        val lecturers = listOf(getLecturer())
        val response = lecturers.map { getLecturerInfo(it) }

        every { lecturerRepository.findAll() } returns lecturers

        assertEquals(response, lecturerService.getAllLecturers())
        verify { lecturerRepository.findAll() }
    }

    @Test
    fun testGetLecturersByChair() {
        val chairId = 1;
        val lecturers = listOf(getLecturer())
        val response = lecturers.map { getLecturerShortInfo(it) }

        every { lecturerRepository.findAllByChairId(chairId) } returns lecturers

        assertEquals(response, lecturerService.getLecturersByChair(chairId))
        verify { lecturerRepository.findAllByChairId(chairId) }
    }

    @Test
    fun testDeleteLecturer() {
        val id = 1;
        every { lecturerRepository.existsById(id) } returns true
        every { lecturerRepository.deleteById(id) } returns mockk()

        lecturerService.deleteLecturer(id)

        verify { lecturerRepository.existsById(id) }
        verify { lecturerRepository.deleteById(id) }
    }

    @Test
    fun testDeleteNonExistingLecturer() {
        val id = 1;
        every { lecturerRepository.existsById(id) } returns false

        assertThrows(NotFoundException::class.java) { lecturerService.deleteLecturer(id) }
        verify { lecturerRepository.existsById(id) }
    }

    @Test
    fun testGetLecturerInfo() {
        val lecturer = getLecturer()
        val response = getLecturerInfo(lecturer)

        every { lecturerRepository.findById(lecturer.id) } returns Optional.of(lecturer)

        assertEquals(response, lecturerService.getLecturerInfo(lecturer.id))
        verify { lecturerRepository.findById(lecturer.id) }
    }
}