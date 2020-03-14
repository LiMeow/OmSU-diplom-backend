package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateFacultyRequest
import omsu.imit.schedule.dto.response.FacultyInfo
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Faculty
import omsu.imit.schedule.repository.FacultyRepository
import omsu.imit.schedule.service.BuildingService
import omsu.imit.schedule.service.FacultyService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class FacultyServiceTests : BaseTests() {
    @MockK
    lateinit var buildingService: BuildingService

    @MockK
    lateinit var facultyRepository: FacultyRepository

    private lateinit var facultyService: FacultyService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.facultyService = FacultyService(buildingService, facultyRepository)
    }

    @Test
    fun testCreateFaculty() {
        val building = getBuilding()
        val faculty = getFaculty()

        val request = CreateFacultyRequest(building.id, faculty.name)
        val response = getFacultyInfo(faculty)

        every { buildingService.getBuildingById(request.buildingId) } returns building
        every { facultyRepository.save(faculty) } returns faculty

        assertEquals(response, facultyService.createFaculty(request))

        verify { buildingService.getBuildingById(request.buildingId) }
        verify { facultyRepository.save(faculty) }
    }

    @Test
    fun testGetFacultyById() {
        val faculty = getFaculty()

        every { facultyRepository.findById(faculty.id) } returns Optional.of(faculty)

        assertEquals(faculty, facultyService.getFacultyById(faculty.id))
        verify { facultyRepository.findById(faculty.id) }
    }

    @Test
    fun testGetNonExistingFacultyById() {
        every { facultyRepository.findById(1) } returns Optional.empty()

        Assertions.assertThrows(NotFoundException::class.java) { facultyService.getFacultyById(1) }
        verify { facultyRepository.findById(1) }
    }

    @Test
    fun testGetFacultyInfo() {
        val faculty = getFaculty()
        val response = getFacultyInfo(faculty)

        every { facultyRepository.findById(faculty.id) } returns Optional.of(faculty)

        assertEquals(response, facultyService.getFacultyInfo(faculty.id))
        verify { facultyRepository.findById(faculty.id) }
    }

    @Test
    fun testGetAllFaculties() {
        val building = getBuilding()
        val faculty1 = Faculty(building, "ИНСТИТУТ МАТЕМАТИКИ И ИНФОРМАЦИОННЫХ ТЕХНОЛОГИЙ")
        val faculty2 = Faculty(building, "ФИЗИЧЕСКИЙ ФАКУЛЬТЕТ")
        val faculties = listOf(faculty1, faculty2)
        val response = faculties.asSequence().map { getFacultyInfo(it) }.toList()

        every { facultyRepository.findAll() } returns faculties

        assertEquals(response, facultyService.getAllFaculties())
        verify { facultyRepository.findAll() }
    }

    @Test
    fun testDeleteFaculty() {
        val id = 1;
        every { facultyRepository.existsById(id) } returns true
        every { facultyRepository.deleteById(id) } returns mockk()

        facultyService.deleteFaculty(id)

        verify { facultyRepository.existsById(id) }
        verify { facultyRepository.deleteById(id) }
    }

    @Test
    fun testDeleteNonExistingFaculty() {
        every { facultyRepository.existsById(1) } returns false

        Assertions.assertThrows(NotFoundException::class.java) { facultyService.deleteFaculty(1) }
        verify { facultyRepository.existsById(1) }
    }

    private fun getFacultyInfo(faculty: Faculty): FacultyInfo {
        return FacultyInfo(
                faculty.id,
                getBuildingInfo(faculty.building),
                faculty.name)
    }
}