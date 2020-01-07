package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.OccupyAuditoryRequest
import omsu.imit.schedule.dto.response.AuditoryInfo
import omsu.imit.schedule.dto.response.OccupationInfo
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.AuditoryOccupation
import omsu.imit.schedule.model.Group
import omsu.imit.schedule.model.Lecturer
import omsu.imit.schedule.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class AuditoryOccupationService
@Autowired
constructor(private val auditoryRepository: AuditoryRepository,
            private val auditoryOccupationRepository: AuditoryOccupationRepository,
            private val groupRepository: GroupRepository,
            private val lecturerRepository: LecturerRepository,
            private val timeBlockRepository: TimeBlockRepository) : BaseService() {


    fun occupyAuditory(auditoryId: Int, request: OccupyAuditoryRequest): OccupationInfo {
        var group: List<Group>? = null
        var lecturer: Lecturer? = null

        if (request.groupIds!!.isNotEmpty()) group = groupRepository.findAllById(request.groupIds!!)
                ?: throw NotFoundException(ErrorCode.GROUP_NOT_EXISTS, request.groupIds.toString())

        if (request.lecturerId != 0) lecturer = lecturerRepository.findById(request.lecturerId!!)
                .orElseThrow { NotFoundException(ErrorCode.LECTURER_NOT_EXISTS, request.lecturerId.toString()) }

        val timeBlock = timeBlockRepository.findByTime(request.timeFrom, request.timeTo)
                ?: throw NotFoundException(ErrorCode.TIMEBLOCK_NOT_EXISTS, request.timeFrom, request.timeTo)

        val auditory = auditoryRepository.findById(auditoryId)
                .orElseThrow { NotFoundException(ErrorCode.AUDITORY_NOT_EXISTS, auditoryId.toString()) }

        val occupation = AuditoryOccupation(
                auditory,
                timeBlock,
                request.date,
                lecturer,
                group!!,
                request.comment)

        auditoryOccupationRepository.save(occupation)
        return toOccupationInfo(occupation)
    }

    fun getAuditoryWithOccupationsByDate(auditoryId: Int, date: String): AuditoryInfo {
        val auditory = auditoryRepository
                .findById(auditoryId)
                .orElseThrow { NotFoundException(ErrorCode.AUDITORY_NOT_EXISTS, auditoryId.toString()) }

        auditory.auditoryOccupations = auditoryOccupationRepository.findByAuditoryAndDate(auditoryId, date)
        return toAuditoryInfo(auditory)
    }

    fun deleteAuditoryOccupation(occupationId: Int) {
        if (!auditoryOccupationRepository.existsById(occupationId))
            throw  NotFoundException(ErrorCode.AUDITORY_OCCUPATION_NOT_EXISTS, occupationId.toString())

        auditoryOccupationRepository.deleteById(occupationId)
    }

    fun deleteAllAuditoryOccupations(auditoryId: Int) {
        auditoryOccupationRepository.deleteAllByAuditory(auditoryId)
    }
}