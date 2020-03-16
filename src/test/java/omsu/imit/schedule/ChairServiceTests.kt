package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateChairRequest
import omsu.imit.schedule.dto.response.ChairInfo
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Chair
import omsu.imit.schedule.repository.ChairRepository
import omsu.imit.schedule.service.ChairService
import omsu.imit.schedule.service.FacultyService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class ChairServiceTests : BaseTests() {
    @MockK
    lateinit var chairRepository: ChairRepository

    @MockK
    lateinit var facultyService: FacultyService

    private lateinit var chairService: ChairService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.chairService = ChairService(
                this.chairRepository,
                this.facultyService)
    }

    @Test
    fun testCreateChair() {
        val faculty = getFaculty()
        val chair = getChair()

        val request = CreateChairRequest(faculty.id, chair.name)
        val response = getChairInfo(chair)

        every { facultyService.getFacultyById(faculty.id) } returns faculty
        every { chairRepository.save(chair) } returns chair

        assertEquals(response, chairService.createChair(request))

        verify { facultyService.getFacultyById(faculty.id) }
        verify { chairRepository.save(chair) }
    }

    @Test
    fun testGetChairById() {
        val chair = getChair()

        every { chairRepository.findById(chair.id) } returns Optional.of(chair)

        assertEquals(chair, chairService.getChairById(chair.id))
        verify { chairRepository.findById(chair.id) }
    }

    @Test
    fun testGetNonExistingChairById() {
        every { chairRepository.findById(1) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { chairService.getChairById(1) }
        verify { chairRepository.findById(1) }
    }

    @Test
    fun testGetAllChairs() {
        val faculty = getFaculty()
        val chair1 = Chair(faculty, "КАФЕДРА КОМПЬЮТЕРНОЙ МАТЕМАТИКИ И ПРОГРАММИРОВАНИЯ")
        val chair2 = Chair(faculty, "КАФЕДРА АЛГЕБРЫ И МАТЕМАТИЧЕСКОГО АНАЛИЗА")
        val chairs = listOf(chair1, chair2)

        val response = chairs.asSequence().map { getChairInfo(it) }.toList()

        every { chairRepository.findAll() } returns chairs

        assertEquals(response, chairService.getAllChairs())
        verify { chairRepository.findAll() }
    }

    @Test
    fun testDeleteChair() {
        val id = 1;
        every { chairRepository.existsById(id) } returns true
        every { chairRepository.deleteById(id) } returns mockk()

        chairService.deleteChair(id)

        verify { chairRepository.existsById(id) }
        verify { chairRepository.deleteById(id) }
    }

    @Test
    fun testDeleteNonExistingChair() {
        every { chairRepository.existsById(1) } returns false

        assertThrows(NotFoundException::class.java) { chairService.deleteChair(1) }
        verify { chairRepository.existsById(1) }
    }

    @Test
    fun testGetChairInfo() {
        val chair = getChair()
        val response = getChairInfo(chair)

        every { chairRepository.findById(chair.id) } returns Optional.of(chair)

        assertEquals(response, chairService.getChairInfo(chair.id))
        verify { chairRepository.findById(chair.id) }
    }

    private fun getChairInfo(chair: Chair): ChairInfo {
        return ChairInfo(chair.id, chair.faculty.name, chair.name)
    }
}