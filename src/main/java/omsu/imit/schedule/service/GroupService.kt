package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateGroupRequest
import omsu.imit.schedule.dto.response.GroupInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Group
import omsu.imit.schedule.repository.GroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class GroupService
@Autowired
constructor(private val courseService: CourseService,
            private val groupRepository: GroupRepository,
            private val studyDirectionService: StudyDirectionService) : BaseService() {

    fun createGroup(request: CreateGroupRequest): GroupInfo {
        val studyDirection = studyDirectionService.getStudyDirectionById(request.studyDirectionId)
        val course = courseService.getCourseById(request.courseId)
        val group = Group(studyDirection, course, request.name, request.formationYear, request.dissolutionYear)

        try {
            groupRepository.save(group)
        } catch (e: DataIntegrityViolationException) {
            throw CommonValidationException(ErrorCode.GROUP_ALREADY_EXISTS, request.name)
        }
        return toGroupInfo(group)
    }

    fun getGroupById(groupId: Int): Group {
        return groupRepository.findById(groupId)
                .orElseThrow { NotFoundException(ErrorCode.GROUP_NOT_EXISTS, groupId.toString()) }
    }

    fun getAllGroups(): List<GroupInfo> {
        return groupRepository.findAll().asSequence().map { toGroupInfo(it) }.toList()
    }

    fun getGroupsByIds(groupIds: List<Int>): List<Group> {
        return groupRepository.findAllById(groupIds) ?: throw NotFoundException(ErrorCode.ONE_OR_MORE_GROUPS_DONT_EXIST)
    }

    fun deleteGroupById(groupId: Int) {
        if (!groupRepository.existsById(groupId))
            throw  NotFoundException(ErrorCode.GROUP_NOT_EXISTS, groupId.toString())

        groupRepository.deleteById(groupId)
    }

    fun getGroupInfo(groupId: Int): GroupInfo {
        return toGroupInfo(getGroupById(groupId))
    }
}
