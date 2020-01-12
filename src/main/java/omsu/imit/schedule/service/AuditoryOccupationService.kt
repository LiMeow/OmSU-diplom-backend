package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.OccupyAuditoryRequest
import omsu.imit.schedule.dto.response.OccupationInfo
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.*
import omsu.imit.schedule.repository.AuditoryOccupationRepository
import omsu.imit.schedule.repository.GroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class AuditoryOccupationService
@Autowired
constructor(private val auditoryService: AuditoryService,
            private val auditoryOccupationRepository: AuditoryOccupationRepository,
            private val groupRepository: GroupRepository,
            private val lecturerService: LecturerService,
            private val timeBlockService: TimeBlockService) : BaseService() {


    fun occupyAuditory(auditoryId: Int, request: OccupyAuditoryRequest): OccupationInfo {
        var groups: List<Group>? = null

        if (request.groupIds!!.isNotEmpty())
            groups = groupRepository.findAllById(request.groupIds!!)
                    ?: throw NotFoundException(ErrorCode.ONE_OR_MORE_GROUPS_DONT_EXIST)

        val lecturer = lecturerService.getLecturer(request.lecturerId)
        val timeBlock = timeBlockService.getTimeBlockByTime(request.timeFrom, request.timeTo)
        val auditory = auditoryService.getAuditoryById(auditoryId)

        val occupation = occupyAuditory(auditory, timeBlock, request.day, request.dateFrom, request.dateTo, request.interval, lecturer, groups!!, request.comment)

        return toOccupationInfo(occupation)
    }

    fun occupyAuditory(auditory: Auditory, timeBlock: TimeBlock,
                       day: Day, dateFrom: String, dateTo: String, interval: Interval,
                       lecturer: Lecturer, groups: List<Group>?, comment: String? = ""): AuditoryOccupation {

        val occupation = AuditoryOccupation(auditory, timeBlock, day, dateFrom, dateTo, interval, lecturer, groups!!, comment)
        auditoryOccupationRepository.save(occupation)
        return occupation
    }

//    fun getAuditoryWithOccupationsByDate(auditoryId: Int, date: String): AuditoryInfo {
//        val auditory = auditoryRepository
//                .findById(auditoryId)
//                .orElseThrow { NotFoundException(ErrorCode.AUDITORY_NOT_EXISTS, auditoryId.toString()) }
//
//        auditory.auditoryOccupations = auditoryOccupationRepository.findByAuditoryAndDate(auditoryId, date)
//        return toAuditoryInfo(auditory)
//    }

    fun deleteAuditoryOccupation(occupationId: Int) {
        if (!auditoryOccupationRepository.existsById(occupationId))
            throw  NotFoundException(ErrorCode.AUDITORY_OCCUPATION_NOT_EXISTS, occupationId.toString())

        auditoryOccupationRepository.deleteById(occupationId)
    }

    fun deleteAllAuditoryOccupations(auditoryId: Int) {
        auditoryOccupationRepository.deleteAllByAuditory(auditoryId)
    }
}