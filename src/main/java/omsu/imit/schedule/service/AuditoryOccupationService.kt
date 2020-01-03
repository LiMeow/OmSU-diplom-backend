package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.AuditoryOccupation
import omsu.imit.schedule.model.Group
import omsu.imit.schedule.model.Lecturer
import omsu.imit.schedule.repository.*
import omsu.imit.schedule.requests.OccupyAuditoryRequest
import omsu.imit.schedule.response.OccupationInfo
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

        if (!request.groups!!.isEmpty()) group = groupRepository.findAllById(request.groups!!)
                ?: throw ScheduleGeneratorException(ErrorCode.GROUP_NOT_EXISTS, request.groups.toString())

        if (request.lecturerId != 0) lecturer = lecturerRepository.findById(request.lecturerId!!).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.LECTURER_NOT_EXISTS, request.lecturerId.toString())

        val timeBlock = timeBlockRepository.findByTime(request.timeFrom, request.timeTo)
                ?: throw ScheduleGeneratorException(ErrorCode.TIMEBLOCK_NOT_EXISTS, request.timeFrom, request.timeTo)

        val auditory = auditoryRepository.findById(auditoryId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.AUDITORY_NOT_EXISTS, auditoryId.toString())

        val occupation = AuditoryOccupation(
                auditory,
                timeBlock,
                request.date,
                lecturer,
                group!!,
                request.comment)

        auditoryOccupationRepository.save(occupation)
        return createOccupationInfo(occupation)
    }

    fun deleteAuditoryOccupation(occupationId: Int) {
        if (!auditoryOccupationRepository.existsById(occupationId))
            throw ScheduleGeneratorException(ErrorCode.AUDITORY_OCCUPATION_NOT_EXISTS, occupationId.toString())

        auditoryOccupationRepository.deleteById(occupationId)
    }

    fun deleteAllAuditoryOccupations(auditoryId: Int) {
        auditoryOccupationRepository.deleteAllByAuditory(auditoryId)
    }
}