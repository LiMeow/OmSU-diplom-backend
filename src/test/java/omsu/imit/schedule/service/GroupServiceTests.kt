package omsu.imit.schedule.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateGroupRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Group
import omsu.imit.schedule.repository.GroupRepository
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.dao.DataIntegrityViolationException
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class GroupServiceTests : BaseTests() {

    @MockK
    lateinit var courseService: CourseService

    @MockK
    lateinit var groupRepository: GroupRepository

    @MockK
    lateinit var studyDirectionService: StudyDirectionService

    private lateinit var groupService: GroupService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.groupService = GroupService(
                this.courseService,
                this.groupRepository,
                this.studyDirectionService)
    }

    @Test
    fun testCreateGroup() {
        val studyDirection = getStudyDirection()
        val course = getCourse()
        val group = getGroup()

        val request = CreateGroupRequest(
                studyDirection.id,
                course.id,
                group.name,
                group.formationYear,
                group.dissolutionYear!!)

        val response = getGroupInfo(group)

        every { studyDirectionService.getStudyDirectionById(request.studyDirectionId) } returns studyDirection
        every { courseService.getCourseById(request.courseId) } returns course
        every { groupRepository.save(group) } returns group

        assertEquals(response, groupService.createGroup(request))

        verify { studyDirectionService.getStudyDirectionById(request.studyDirectionId) }
        verify { courseService.getCourseById(request.courseId) }
        verify { groupRepository.save(group) }
    }

    @Test
    fun testCreateAlreadyExistingGroup() {
        val studyDirection = getStudyDirection()
        val course = getCourse()
        val group = getGroup()

        val request = CreateGroupRequest(
                studyDirection.id,
                course.id,
                group.name,
                group.formationYear,
                group.dissolutionYear!!)

        every { studyDirectionService.getStudyDirectionById(request.studyDirectionId) } returns studyDirection
        every { courseService.getCourseById(request.courseId) } returns course
        every { groupRepository.save(group) } throws DataIntegrityViolationException("")

        assertThrows(CommonValidationException::class.java) { groupService.createGroup(request) }

        verify { studyDirectionService.getStudyDirectionById(request.studyDirectionId) }
        verify { courseService.getCourseById(request.courseId) }
        verify { groupRepository.save(group) }
    }

    @Test
    fun testGroupById() {
        val group = getGroup()

        every { groupRepository.findById(group.id) } returns Optional.of(group)

        assertEquals(group, groupService.getGroupById(group.id))
        verify { groupRepository.findById(group.id) }
    }

    @Test
    fun testGroupByNonExistingId() {
        val id = 1

        every { groupRepository.findById(id) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { groupService.getGroupById(id) }
        verify { groupRepository.findById(id) }
    }

    @Test
    fun testAllGroups() {
        val group1 = Group(1, getStudyDirection(), getCourse(), "МПБ-601-О", "2016")
        val group2 = Group(2, getStudyDirection(), getCourse(), "МПБ-602-О", "2016")
        val groups = listOf(group1, group2)
        val response = groups.asSequence().map { getGroupInfo(it) }.toList()

        every { groupRepository.findAll() } returns groups

        assertEquals(response, groupService.getAllGroups())
        verify { groupRepository.findAll() }
    }

    @Test
    fun testGroupsByIds() {
        val group1 = Group(1, getStudyDirection(), getCourse(), "МПБ-601-О", "2016")
        val group2 = Group(2, getStudyDirection(), getCourse(), "МПБ-602-О", "2016")
        val groups = listOf(group1, group2)
        val groupsIds = listOf(group1.id, group2.id)

        every { groupRepository.findAllById(groupsIds) } returns groups

        assertEquals(groups, groupService.getGroupsByIds(groupsIds))
        verify { groupRepository.findAllById(groupsIds) }
    }

    @Test
    fun testDeleteGroupsById() {
        val id = 1;

        every { groupRepository.existsById(id) } returns true
        every { groupRepository.deleteById(id) } returns mockk()

        groupService.deleteGroupById(id)

        verify { groupRepository.existsById(id) }
        verify { groupRepository.deleteById(id) }
    }

    @Test
    fun testDeleteGroupsByNonExistingId() {
        val id = 1;

        every { groupRepository.existsById(id) } returns false

        assertThrows(NotFoundException::class.java) { groupService.deleteGroupById(id) }
        verify { groupRepository.existsById(id) }
    }

    @Test
    fun testGetGroupInfo() {
        val group = getGroup()
        val response = getGroupInfo(group)

        every { groupRepository.findById(group.id) } returns Optional.of(group)

        assertEquals(response, groupService.getGroupInfo(group.id))
        verify { groupRepository.findById(group.id) }
    }
}