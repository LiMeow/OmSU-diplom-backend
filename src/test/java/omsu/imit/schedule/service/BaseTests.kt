package omsu.imit.schedule.service

import omsu.imit.schedule.dto.response.*
import omsu.imit.schedule.model.*
import java.time.LocalDate

open class BaseTests {

    fun getBuilding(): Building {
        return Building(1, " пр. Мира, 55-а")
    }

    fun getChair(): Chair {
        return Chair(getFaculty(), "КАФЕДРА КОМПЬЮТЕРНОЙ МАТЕМАТИКИ И ПРОГРАММИРОВАНИЯ")
    }

    fun getClassroom(): Classroom {
        return Classroom(getBuilding(), "214")
    }

    fun getClassroomWithTags(): Classroom {
        return Classroom(0, getBuilding(), "214", getTags())
    }

    fun getCourse(): Course {
        return Course(getFaculty(), "2016", "2020")
    }

    fun getEvent(): Event {
        return Event(getLecturer(), "", true)
    }

    fun getEventPeriod(dateFrom: LocalDate = LocalDate.of(2020, 5, 1),
                       dateTo: LocalDate = LocalDate.of(2020, 7, 1),
                       day: Day = Day.MONDAY,
                       interval: Interval = Interval.EVERY_WEEK): EventPeriod {
        return EventPeriod(getEvent(), getClassroom(), getTimeBlock(), day, dateFrom, dateTo, interval)
    }

    fun getGroup(): Group {
        return Group(
                getStudyDirection(),
                getCourse(),
                "МПБ-604-О",
                "2016",
                "2020")
    }

    fun getFaculty(): Faculty {
        return Faculty(getBuilding(), "ИНСТИТУТ МАТЕМАТИКИ И ИНФОРМАЦИОННЫХ ТЕХНОЛОГИЙ")
    }

    fun getLecturer(): Lecturer {
        return Lecturer(getChair(), getLecturerPersonalData())
    }

    fun getStudyDirection(): StudyDirection {
        return StudyDirection(
                1,
                getFaculty(),
                "01.03.02",
                "Прикладная математика и информатика",
                Qualification.BACHELOR,
                StudyForm.FULL_TIME)
    }

    fun getTag(): Tag {
        return Tag(0, "TAG")
    }

    fun getTags(): MutableList<Tag> {
        val tag1 = Tag(1, "TAG1")
        val tag2 = Tag(2, "TAG2")
        return mutableListOf(tag1, tag2)
    }

    fun getTimeBlock(): TimeBlock {
        return TimeBlock("8:00", "9:35")
    }

    fun getUser(enabled: Boolean = true, role: Role = Role.ROLE_USER): User {
        return User(0,
                "FirstName",
                "Patronymic",
                "LastName",
                "example@gmail.com",
                "password",
                role,
                enabled)
    }

    fun getLecturerPersonalData(): User {
        return User(0,
                "FirstName",
                "Patronymic",
                "LastName",
                "example@gmail.com",
                null,
                Role.ROLE_LECTURER,
                false)
    }

    fun getBuildingInfo(building: Building): BuildingInfo {
        return BuildingInfo(building.id, building.number, building.address)
    }

    fun getClassroomShortInfo(classroom: Classroom): ClassroomShortInfo {
        return ClassroomShortInfo(
                classroom.id,
                classroom.building.number,
                classroom.number)
    }

    fun getClassroomInfo(classroom: Classroom): ClassroomInfo {
        return ClassroomInfo(
                classroom.id,
                classroom.building.number,
                classroom.number,
                listOf())
    }

    fun getChairInfo(chair: Chair): ChairInfo {
        return ChairInfo(chair.id, chair.faculty.name, chair.name)
    }

    fun getEventInfo(event: Event): EventInfo {
        return return EventInfo(
                event.id,
                getLecturerShortInfo(event.lecturer),
                event.comment,
                event.required,
                event.eventPeriods
                        .asSequence()
                        .sortedBy { eventPeriod -> eventPeriod.dateFrom }
                        .map { getEventPeriodInfo(it) }
                        .toMutableList())
    }

    private fun getEventPeriodInfo(period: EventPeriod): EventPeriodInfo {
        return EventPeriodInfo(
                period.id,
                getClassroomShortInfo(period.classroom),
                period.timeBlock,
                period.day,
                period.dateFrom,
                period.dateTo,
                period.interval)
    }

    fun getGroupInfo(group: Group): GroupInfo {
        return GroupInfo(group.id,
                group.name,
                getStudyDirectionInfo(group.studyDirection),
                group.formationYear,
                group.dissolutionYear)
    }

    fun getStudyDirectionInfo(studyDirection: StudyDirection): StudyDirectionInfo {
        return StudyDirectionInfo(
                studyDirection.id,
                studyDirection.faculty.name,
                studyDirection.code,
                studyDirection.name,
                studyDirection.qualification.description,
                studyDirection.studyForm.form)
    }

    fun getLecturerInfo(lecturer: Lecturer): LecturerInfo {
        return LecturerInfo(lecturer.id, lecturer.getFullName(), getChairInfo(lecturer.chair))
    }

    fun getLecturerShortInfo(lecturer: Lecturer): LecturerShortInfo {
        return LecturerShortInfo(
                lecturer.id,
                lecturer.user.firstName,
                lecturer.user.patronymic,
                lecturer.user.lastName,
                lecturer.user.email)
    }
}