package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateGroupRequest
import omsu.imit.schedule.dto.response.GroupInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Group
import omsu.imit.schedule.repository.GroupRepository
import omsu.imit.schedule.repository.StudyDirectionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GroupService
@Autowired
constructor(
        private val groupRepository: GroupRepository,
        private val studyDirectionRepository: StudyDirectionRepository) : BaseService() {

    fun createGroup(request: CreateGroupRequest): GroupInfo {
        val studyDirection = studyDirectionRepository.findById(request.studyDirectionId)
                .orElseThrow { NotFoundException(ErrorCode.STUDY_DIRECTION_NOT_EXISXTS, request.studyDirectionId.toString()) }

        if (groupRepository.findByNameAndDirection(request.name, request.studyDirectionId) != null)
            throw CommonValidationException(ErrorCode.GROUP_ALREADY_EXISTS, request.name)

        val group = Group(studyDirection, request.name)
        groupRepository.save(group)

        return toGroupInfo(group)
    }

    fun getGroupById(groupId: Int): GroupInfo {
        val group = groupRepository.findById(groupId)
                .orElseThrow { NotFoundException(ErrorCode.GROUP_NOT_EXISTS, groupId.toString()) }
        return toGroupInfo(group)
    }

    fun getAllGroups(): List<GroupInfo> {
        return groupRepository.findAll().asSequence().map { toGroupInfo(it) }.toList()
    }

    fun deleteGroupById(groupId: Int) {
        if (!groupRepository.existsById(groupId))
            throw  NotFoundException(ErrorCode.GROUP_NOT_EXISTS, groupId.toString())

        groupRepository.deleteById(groupId)
    }
}
