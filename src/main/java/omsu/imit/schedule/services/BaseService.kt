package omsu.imit.schedule.services

import omsu.imit.schedule.dto.response.*
import omsu.imit.schedule.model.*
import org.springframework.stereotype.Service

@Service
class BaseService {

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
        return ChairInfo(chair.id,
                chair.faculty.name,
                chair.name)
    }

    fun toClassroomInfo(classroom: Classroom): ClassroomInfo {
        return ClassroomInfo(
                classroom.id,
                classroom.building.number,
                classroom.number,
                classroom.tags)
    }

    fun toClassroomShortInfo(classroom: Classroom): ClassroomShortInfo {
        return ClassroomShortInfo(
                classroom.id,
                classroom.building.number,
                classroom.number)
    }

    fun toCourseInfo(course: Course): CourseInfo {
        return CourseInfo(
                course.id,
                course.faculty.name,
                course.startYear,
                course.finishYear,
                course.groups?.asSequence()?.map { toGroupInfo(it) }?.toList())
    }

    private fun toEventPeriodInfo(period: EventPeriod): EventPeriodInfo {
        return EventPeriodInfo(
                period.id,
                toClassroomShortInfo(period.classroom),
                period.timeBlock,
                period.day,
                period.dateFrom,
                period.dateTo,
                period.interval)
    }

    fun toEventPeriodShortInfo(period: EventPeriod): EventPeriodInfo {
        return EventPeriodInfo(
                period.id,
                null,
                period.timeBlock,
                period.day,
                period.dateFrom,
                period.dateTo,
                period.interval)
    }

    fun toEventInfo(event: Event): EventInfo {
        return EventInfo(
                event.id,
                toLecturerShortInfo(event.lecturer),
                event.comment,
                event.required,
                event.eventPeriods
                        .asSequence()
                        .sortedBy { eventPeriod -> eventPeriod.dateFrom }
                        .map { toEventPeriodInfo(it) }
                        .toMutableList())
    }

    fun toEventShortInfo(event: Event): EventInfo {
        return EventInfo(
                event.id,
                toLecturerShortInfo(event.lecturer),
                event.comment,
                event.required,
                mutableListOf())
    }

    fun toFacultyInfo(faculty: Faculty): FacultyInfo {
        return FacultyInfo(
                faculty.id,
                toBuildingInfo(faculty.building),
                faculty.name)
    }

    fun toGroupInfo(group: Group): GroupInfo {
        return GroupInfo(group.id,
                group.name,
                toStudyDirectionInfo(group.studyDirection),
                group.formationYear,
                group.dissolutionYear)
    }

    private fun toGroupShortInfo(group: Group): GroupShortInfo {
        return GroupShortInfo(group.id, group.name)
    }

    fun toLecturerInfo(lecturer: Lecturer): LecturerInfo {
        return LecturerInfo(
                lecturer.id,
                lecturer.user.firstName,
                lecturer.user.patronymic,
                lecturer.user.lastName,
                toChairInfo(lecturer.chair))
    }

    fun toLecturerShortInfo(lecturer: Lecturer): LecturerShortInfo {
        return LecturerShortInfo(
                lecturer.id,
                lecturer.user.firstName,
                lecturer.user.patronymic,
                lecturer.user.lastName,
                lecturer.user.email)
    }

    fun toScheduleItemInfo(
            scheduleItem: ScheduleItem,
            eventPeriod: EventPeriod,
            forGroup: Boolean = false,
            forLecturer: Boolean = false): ScheduleItemInfo {
        val scheduleInfo = ScheduleItemInfo(
                scheduleItem.id,
                eventPeriod.dateFrom,
                eventPeriod.dateTo,
                eventPeriod.timeBlock,
                eventPeriod.interval,
                eventPeriod.classroom.building.number,
                eventPeriod.classroom.number,
                scheduleItem.discipline.name,
                scheduleItem.activityType,
                scheduleItem.event.comment)
        if (!forGroup)
            scheduleInfo.groups = scheduleItem.groups.asSequence().map { toGroupShortInfo(it) }.toList()

        if (!forLecturer)
            scheduleInfo.lecturer = toLecturerShortInfo(scheduleItem.event.lecturer)
        return scheduleInfo
    }

    fun toStudyDirectionInfo(studyDirection: StudyDirection): StudyDirectionInfo {
        return StudyDirectionInfo(
                studyDirection.id,
                studyDirection.faculty.name,
                studyDirection.code,
                studyDirection.name,
                studyDirection.qualification.description,
                studyDirection.studyForm.form)
    }
}