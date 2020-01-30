package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.OccupyAuditoryRequest
import omsu.imit.schedule.dto.response.OccupationInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.*
import omsu.imit.schedule.repository.AuditoryOccupationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date


@Service
class AuditoryOccupationService
@Autowired
constructor(private val auditoryService: AuditoryService,
            private val auditoryOccupationRepository: AuditoryOccupationRepository,
            private val groupService: GroupService,
            private val lecturerService: LecturerService,
            private val timeBlockService: TimeBlockService) : BaseService() {


    fun occupyAuditory(auditoryId: Int, request: OccupyAuditoryRequest): OccupationInfo {
        var groups: List<Group>? = null

        if (request.groupIds!!.isNotEmpty()) groups = groupService.getGroupsByIds(request.groupIds!!)

        val lecturer = lecturerService.getLecturer(request.lecturerId)
        val timeBlock = timeBlockService.getTimeBlockByTime(request.timeFrom, request.timeTo)
        val auditory = auditoryService.getAuditoryById(auditoryId)

        val occupation = occupyAuditory(
                auditory,
                timeBlock,
                request.day,
                request.dateFrom,
                request.dateTo,
                request.interval,
                lecturer, groups!!,
                request.comment)

        return toOccupationInfo(occupation)
    }

    fun occupyAuditory(auditory: Auditory, timeBlock: TimeBlock,
                       day: Day, dateFrom: Date, dateTo: Date, interval: Interval,
                       lecturer: Lecturer, groups: List<Group>?, comment: String? = ""): AuditoryOccupation {


        val occupations = auditoryOccupationRepository.findByAuditoryDayAndTime(auditoryId = auditory.id, day = day, timeBlockId = timeBlock.id).asSequence()
                .filter {
                    (it.dateFrom.before(dateTo) && it.dateTo.after(dateFrom)) &&
                            (it.interval == Interval.EVERY_WEEK ||
                                    (it.interval == Interval.ONLY_EVEN_WEEKS && interval == Interval.EVERY_WEEK) ||
                                    (it.interval == Interval.ONLY_ODD_WEEKS && interval == Interval.EVERY_WEEK))
                }
                .toList()

        if (occupations.isNotEmpty()) {
            throw throw CommonValidationException(ErrorCode.AUDITORY_ALREADY_BUSY,
                    auditory.id.toString(), timeBlock.timeFrom, timeBlock.timeTo, day.name)
        }

        val occupation = AuditoryOccupation(auditory, timeBlock, day, dateFrom, dateTo, interval, lecturer, groups!!, comment)
        auditoryOccupationRepository.save(occupation)
        return occupation
    }

    fun getOccupationInfo(occupationId: Int): OccupationInfo {
        return toOccupationInfo(getOccupationById(occupationId))
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

    private fun getOccupationById(occupationId: Int): AuditoryOccupation {
        return auditoryOccupationRepository
                .findById(occupationId)
                .orElseThrow { NotFoundException(ErrorCode.AUDITORY_OCCUPATION_NOT_EXISTS, occupationId.toString()) }
    }
}