package omsu.imit.schedule.service

import omsu.imit.schedule.dto.response.*
import omsu.imit.schedule.model.*
import org.springframework.stereotype.Service

@Service
open class BaseService {

    fun toGroupInfo(group: Group): GroupInfo {
        return GroupInfo(group.id,
                group.name,
                toStudyDirectionInfo(group.studyDirection),
                group.formationYear,
                group.dissolutionYear)
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
        return ChairInfo(chair.id,
                chair.faculty.name,
                chair.name)
    }

    fun toClassroomInfo(classroom: Classroom): ClassroomInfo {
        return ClassroomInfo(
                classroom.id,
                classroom.number,
                classroom.tags,
                toEventInfo(classroom.events))
    }

    fun toClassroomShortInfo(classroom: Classroom): ClassroomShortInfo {
        return ClassroomShortInfo(
                classroom.id,
                classroom.number,
                classroom.tags)
    }

    fun toEventInfo(event: Event): EventInfo {
        val eventInfo = EventInfo(
                event.id,
                event.day,
                event.timeBlock.timeFrom,
                event.timeBlock.timeTo,
                event.dateFrom,
                event.dateTo,
                event.interval,
                event.required,
                toClassroomShortInfo(event.classroom),
                event.lecturer.getFullName(),
                event.comment!!)


        if (event.groups!!.isNotEmpty())
            eventInfo.group = event.groups!!.asSequence().map { toGroupInfo(it) }.toList()

        return eventInfo
    }

    private fun toEventInfo(events: List<Event>?): List<EventInfo> {
        val response: MutableList<EventInfo> = mutableListOf()

        if (events != null && events.isNotEmpty()) {
            for (event in events) {
                response.add(toEventInfo(event))
            }
        }
        return response
    }

    fun toLecturerInfo(lecturer: Lecturer): LecturerInfo {
        return LecturerInfo(
                lecturer.id,
                lecturer.getFullName(),
                toChairInfo(lecturer.chair))
    }

    fun toScheduleItemInfo(scheduleItem: ScheduleItem): ScheduleItemInfo {
        return ScheduleItemInfo(
                scheduleItem.id,
                scheduleItem.event.dateFrom,
                scheduleItem.event.dateTo,
                scheduleItem.event.interval.description,
                scheduleItem.event.classroom.building.number,
                scheduleItem.event.classroom.number,
                scheduleItem.event.lecturer.getFullName(),
                scheduleItem.event.groups!!.asSequence().map { it.name }.toList(),
                scheduleItem.discipline.name,
                scheduleItem.activityType.description,
                scheduleItem.event.comment!!)
    }
}