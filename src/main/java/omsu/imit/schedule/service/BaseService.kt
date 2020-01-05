package omsu.imit.schedule.service

import omsu.imit.schedule.model.Auditory
import omsu.imit.schedule.model.AuditoryOccupation
import omsu.imit.schedule.response.AuditoryInfo
import omsu.imit.schedule.response.OccupationInfo
import org.springframework.stereotype.Service

@Service
open class BaseService {

    protected fun createAuditoryInfo(auditory: Auditory): AuditoryInfo {
        return AuditoryInfo(
                auditory.id,
                auditory.number,
                auditory.tags,
                createOccupationInfo(auditory.auditoryOccupations))
    }

    protected fun createOccupationInfo(occupation: AuditoryOccupation): OccupationInfo {
        val occupationInfo = OccupationInfo(
                occupation.id,
                occupation.timeBlock.timeFrom,
                occupation.timeBlock.timeTo,
                occupation.date,
                occupation.comment!!)

        if (occupation.lecturer != null)
            occupationInfo.occupying = occupation.lecturer!!.getFullName()

        if (occupation.groups!!.isNotEmpty())
            occupationInfo.group = occupation.groups!![0]

        return occupationInfo
    }

    protected fun createOccupationInfo(occupations: List<AuditoryOccupation>?): List<OccupationInfo> {
        val response: MutableList<OccupationInfo> = mutableListOf()

        if (occupations != null && occupations.isNotEmpty()) {
            for (occupation in occupations) {
                response.add(createOccupationInfo(occupation))
            }
        }
        return response
    }
}