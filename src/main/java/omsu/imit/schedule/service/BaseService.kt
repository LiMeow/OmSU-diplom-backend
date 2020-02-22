package omsu.imit.schedule.service

import omsu.imit.schedule.dto.response.*
import omsu.imit.schedule.model.*
import org.springframework.stereotype.Service

@Service
open class BaseService {

    fun toGroupInfo(group: Group): GroupInfo {
        return GroupInfo(group.id,
                group.name,
                toStudyDirectionInfo(group.studyDirection))
    }

    fun toStudyDirectionInfo(studyDirection: StudyDirection): StudyDirectionInfo {
        return StudyDirectionInfo(studyDirection.id,
                studyDirection.faculty.name,
                studyDirection.code,
                studyDirection.name,
                studyDirection.qualification.description,
                studyDirection.studyForm.form)
    }

    fun toFacultyInfo(faculty: Faculty): FacultyInfo {
        return FacultyInfo(
                faculty.id,
                toBuildingInfo(faculty.building),
                faculty.name)
    }

    fun toBuildingInfo(building: Building): BuildingInfo {
        return BuildingInfo(
                building.id,
                building.number,
                building.address)
    }

    fun toChairsInfo(chairs: List<Chair>): List<ChairInfo> {
        return chairs.asSequence().map { toChairInfo(it) }.toList()
    }

    fun toChairInfo(chair: Chair): ChairInfo {
        return ChairInfo(chair.id, chair.name)
    }

    fun toAuditoryInfo(auditory: Auditory): AuditoryInfo {
        return AuditoryInfo(
                auditory.id,
                auditory.number,
                auditory.tags,
                toOccupationInfo(auditory.auditoryOccupations))
    }

    fun toAuditoryShortInfo(auditory: Auditory): AuditoryShortInfo {
        return AuditoryShortInfo(
                auditory.id,
                auditory.number,
                auditory.tags)
    }

    fun toOccupationInfo(occupation: AuditoryOccupation): OccupationInfo {
        val occupationInfo = OccupationInfo(
                occupation.id,
                occupation.day,
                occupation.timeBlock.timeFrom,
                occupation.timeBlock.timeTo,
                occupation.dateFrom,
                occupation.dateTo,
                occupation.interval,
                toAuditoryShortInfo(occupation.auditory),
                occupation.lecturer.getFullName(),
                occupation.comment!!)


        if (occupation.groups!!.isNotEmpty())
            occupationInfo.group = occupation.groups!!.asSequence().map { toGroupInfo(it) }.toList()

        return occupationInfo
    }

    fun toOccupationInfo(occupations: List<AuditoryOccupation>?): List<OccupationInfo> {
        val response: MutableList<OccupationInfo> = mutableListOf()

        if (occupations != null && occupations.isNotEmpty()) {
            for (occupation in occupations) {
                response.add(toOccupationInfo(occupation))
            }
        }
        return response
    }

    fun toLecturerInfo(lecturer: Lecturer): LecturerInfo {
        return LecturerInfo(
                lecturer.id,
                lecturer.getFullName(),
                lecturer.chair.name,
                lecturer.user.enabled)
    }

    fun toScheduleItemInfo(scheduleItem: ScheduleItem): ScheduleItemInfo {
        return ScheduleItemInfo(
                scheduleItem.id,
                scheduleItem.auditoryOccupation.dateFrom,
                scheduleItem.auditoryOccupation.dateTo,
                scheduleItem.auditoryOccupation.interval.description,
                scheduleItem.auditoryOccupation.auditory.building.number,
                scheduleItem.auditoryOccupation.auditory.number,
                scheduleItem.auditoryOccupation.lecturer.getFullName(),
                scheduleItem.auditoryOccupation.groups!!.asSequence().map { it.name }.toList(),
                scheduleItem.discipline.name,
                scheduleItem.activityType.description,
                scheduleItem.auditoryOccupation.comment!!)
    }
}