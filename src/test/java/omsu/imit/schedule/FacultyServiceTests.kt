package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateFacultyRequest
import omsu.imit.schedule.dto.response.FacultyInfo
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.model.Faculty
import omsu.imit.schedule.repository.FacultyRepository
import omsu.imit.schedule.service.BuildingService
import omsu.imit.schedule.service.FacultyService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith
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
        val building = Building(1, 1, " пр. Мира, 55-а")
        val faculty = Faculty(0, building, "ИНСТИТУТ МАТЕМАТИКИ И ИНФОРМАЦИОННЫХ ТЕХНОЛОГИЙ")

        val request = CreateFacultyRequest(building.id, faculty.name)
        val response = getFacultyInfo(faculty)

        every { buildingService.getBuildingById(request.buildingId) } returns building
        every { facultyRepository.save(faculty) } returns faculty

        assertEquals(response, facultyService.createFaculty(request))

        verify { buildingService.getBuildingById(request.buildingId) }
        verify { facultyRepository.save(faculty) }
    }

    private fun getFacultyInfo(faculty: Faculty): FacultyInfo {
        return FacultyInfo(
                faculty.id,
                getBuildingInfo(faculty.building),
                faculty.name)
    }
}