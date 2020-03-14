package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateClassroomRequest
import omsu.imit.schedule.dto.response.ClassroomShortInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Classroom
import omsu.imit.schedule.repository.ClassroomRepository
import omsu.imit.schedule.service.BuildingService
import omsu.imit.schedule.service.ClassroomService
import omsu.imit.schedule.service.TagService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.dao.DataIntegrityViolationException
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class ClassroomServiceTests : BaseTests() {

    @MockK
    lateinit var buildingService: BuildingService

    @MockK
    lateinit var classroomRepository: ClassroomRepository

    @MockK
    lateinit var tagService: TagService

    private lateinit var classroomService: ClassroomService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.classroomService = ClassroomService(
                this.buildingService,
                this.classroomRepository,
                this.tagService)
    }


    @Test
    fun testCreateClassroomWithoutTags() {
        val building = getBuilding()
        val classroom = getClassroom()
        val request = CreateClassroomRequest(building.id, classroom.number)
        val response = getClassroomShortInfo(classroom)

        every { buildingService.getBuildingById(building.id) } returns building
        every { classroomRepository.save(classroom) } returns classroom

        assertEquals(response, classroomService.createClassroom(request))

        verify { buildingService.getBuildingById(building.id) }
        verify { classroomRepository.save(classroom) }

    }

    @Test
    fun testCreateClassroomWithTags() {
        val building = getBuilding()
        val classroom = getClassroomWithTags()
        val tags = getTags()

        val request = CreateClassroomRequest(building.id, classroom.number, tags.map { it.id }.toList())
        val response = getClassroomShortInfo(classroom)

        every { buildingService.getBuildingById(building.id) } returns building
        every { classroomRepository.save(classroom) } returns classroom
        every { tagService.getAllTagsByIds(request.tags!!) } returns tags

        assertEquals(response, classroomService.createClassroom(request))

        verify { buildingService.getBuildingById(building.id) }
        verify { classroomRepository.save(classroom) }

    }

    @Test
    fun testCreateAlreadyExistingClassroom() {
        val building = getBuilding()
        val classroom = getClassroom()
        val request = CreateClassroomRequest(building.id, classroom.number)

        every { buildingService.getBuildingById(building.id) } returns building
        every { classroomRepository.save(classroom) } throws DataIntegrityViolationException("")

        assertThrows(CommonValidationException::class.java) { classroomService.createClassroom(request) }

        verify { buildingService.getBuildingById(building.id) }
        verify { classroomRepository.save(classroom) }
    }

    @Test
    fun testGetClassroomById() {
        val classroom = getClassroom()

        every { classroomRepository.findById(classroom.id) } returns Optional.of(classroom)

        assertEquals(classroom, classroomService.getClassroomById(classroom.id))
        verify { classroomRepository.findById(classroom.id) }
    }

    @Test
    fun testGetClassroomByNonExistingId() {
        val id = 1

        every { classroomRepository.findById(id) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { classroomService.getClassroomById(id) }
        verify { classroomRepository.findById(id) }
    }

    @Test
    fun testGetClassroomByTags() {
        val classroom = getClassroomWithTags()
        val tags = classroom.tags.map { it.tag }.toList()
        val response = listOf(getClassroomShortInfo(classroom))

        every { classroomRepository.findAllByTags(tags) } returns listOf(classroom)

        assertEquals(response, classroomService.getClassroomsByTags(tags))
        verify { classroomRepository.findAllByTags(tags) }
    }


    private fun getClassroomShortInfo(classroom: Classroom): ClassroomShortInfo {
        return ClassroomShortInfo(
                classroom.id,
                classroom.building.number,
                classroom.number)
    }
}