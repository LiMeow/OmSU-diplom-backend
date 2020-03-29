package omsu.imit.schedule.service

import omsu.imit.schedule.dto.response.*
import omsu.imit.schedule.model.*

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
        return Tag("tag")
    }

    fun getTags(): MutableList<Tag> {
        val tag1 = Tag(1, "tag1")
        val tag2 = Tag(2, "tag2")
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
}