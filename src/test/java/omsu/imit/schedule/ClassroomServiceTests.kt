package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateClassroomRequest
import omsu.imit.schedule.dto.request.EditClassroomRequest
import omsu.imit.schedule.dto.response.ClassroomShortInfo
import omsu.imit.schedule.dto.response.ClassroomsByBuildingInfo
import omsu.imit.schedule.dto.response.MetaInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.model.Classroom
import omsu.imit.schedule.model.Tag
import omsu.imit.schedule.repository.ClassroomRepository
import omsu.imit.schedule.service.BuildingService
import omsu.imit.schedule.service.ClassroomService
import omsu.imit.schedule.service.TagService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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

    @Test
    fun testGetAllClassroomsByBuilding() {
        val building = getBuilding()

        val classroom1 = Classroom(1, building, "213")
        val classroom2 = Classroom(2, building, "214")
        val classroom3 = Classroom(3, building, "215")
        val classrooms = listOf(classroom1, classroom2, classroom3)

        val page = 0
        val size = 3
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("number"))
        val response = ClassroomsByBuildingInfo(
                getMetaInfo(building, classrooms.size, page, size, 0),
                classrooms.map { getClassroomShortInfo(it) }.toList())

        every { buildingService.getBuildingById(building.id) } returns building
        every { classroomRepository.findAllByBuilding(building.id, pageable) } returns classrooms
        every { classroomRepository.countClassroomssByBuilding(building) } returns classrooms.size.toLong()

        assertEquals(response, classroomService.getAllClassroomsByBuilding(building.id, page, size))

        verify { buildingService.getBuildingById(building.id) }
        verify { classroomRepository.findAllByBuilding(building.id, pageable) }
        verify { classroomRepository.countClassroomssByBuilding(building) }
    }

    @Test
    fun testGetAllClassroomsByBuildingwithPageable() {
        val building = getBuilding()

        val classroom1 = Classroom(1, building, "213")
        val classroom2 = Classroom(2, building, "214")
        val classroom3 = Classroom(3, building, "215")
        val classrooms = listOf(classroom1, classroom2, classroom3)

        val page = 1
        val size = 1
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("number"))
        val response = ClassroomsByBuildingInfo(
                getMetaInfo(building, classrooms.size, page, size, 2),
                listOf(getClassroomShortInfo(classroom2)))

        every { buildingService.getBuildingById(building.id) } returns building
        every { classroomRepository.findAllByBuilding(building.id, pageable) } returns listOf(classroom2)
        every { classroomRepository.countClassroomssByBuilding(building) } returns classrooms.size.toLong()

        assertEquals(response, classroomService.getAllClassroomsByBuilding(building.id, page, size))

        verify { buildingService.getBuildingById(building.id) }
        verify { classroomRepository.findAllByBuilding(building.id, pageable) }
        verify { classroomRepository.countClassroomssByBuilding(building) }
    }

    @Test
    fun testEditClassroom() {
        val tag1 = Tag(1, "tag1")
        val tag2 = Tag(2, "tag2")
        val tag3 = Tag(3, "tag3")

        val classroom = Classroom(1, getBuilding(), "214", listOf(tag1, tag2))
        val updatedClassroom = Classroom(1, getBuilding(), "241", listOf(tag3))
        val request = EditClassroomRequest(updatedClassroom.number, listOf(tag3.id))
        val response = getClassroomShortInfo(updatedClassroom)

        every { classroomRepository.findById(classroom.id) } returns Optional.of(classroom)
        every { classroomRepository.save(updatedClassroom) } returns updatedClassroom
        every { tagService.getAllTagsByIds(request.tags!!) } returns mutableListOf(tag3)

        assertEquals(response, classroomService.editClassroom(classroom.id, request))

        verify { classroomRepository.findById(classroom.id) }
        verify { classroomRepository.save(updatedClassroom) }
        verify { tagService.getAllTagsByIds(request.tags!!) }

    }

    @Test
    fun testEditClassroomByEmptyRequest() {
        val classroom = getClassroomWithTags()
        val request = EditClassroomRequest("", listOf())
        val response = getClassroomShortInfo(classroom)

        every { classroomRepository.findById(classroom.id) } returns Optional.of(classroom)
        every { classroomRepository.save(classroom) } returns classroom

        assertEquals(response, classroomService.editClassroom(classroom.id, request))

        verify { classroomRepository.findById(classroom.id) }
        verify { classroomRepository.save(classroom) }
    }

    @Test
    fun testDeleteClassroom() {
        val id = 1;

        every { classroomRepository.existsById(id) } returns true
        every { classroomRepository.deleteById(id) } returns mockk()

        classroomService.deleteClassroom(id)

        verify { classroomRepository.existsById(id) }
        verify { classroomRepository.deleteById(id) }
    }

    @Test
    fun testDeleteNonExistingClassroom() {
        val id = 1;

        every { classroomRepository.existsById(id) } returns false

        assertThrows(NotFoundException::class.java) { classroomService.deleteClassroom(id) }
        verify { classroomRepository.existsById(id) }
    }

    @Test
    fun testGetClassroomInfo() {
        val classroom = getClassroom()
        val response = getClassroomShortInfo(classroom)

        every { classroomRepository.findById(classroom.id) } returns Optional.of(classroom)

        assertEquals(response, classroomService.getClassroomInfo(classroom.id))
        verify { classroomRepository.findById(classroom.id) }
    }


    private fun getClassroomShortInfo(classroom: Classroom): ClassroomShortInfo {
        return ClassroomShortInfo(
                classroom.id,
                classroom.building.number,
                classroom.number)
    }

    private fun getMetaInfo(building: Building,
                            total: Int,
                            page: Int, size: Int,
                            last: Int): MetaInfo {
        val baseUrl = "api/buildings/"

        var nextPage: String? = null
        var prevPage: String? = null
        val firstPage = "${baseUrl}${building.id}/classrooms?page=0&size=${size}"
        val lastPage = "${baseUrl}${building.id}?page=${last}&size=${size}"

        if (last > page)
            nextPage = "${baseUrl}${building.id}/classrooms?page=${page + 1}&size=${size}"
        if (page != 0)
            prevPage = "${baseUrl}${building.id}/classrooms?page=${page - 1}&size=${size}"

        return MetaInfo(
                total,
                page,
                size,
                nextPage,
                prevPage,
                firstPage,
                lastPage

        )
    }
}