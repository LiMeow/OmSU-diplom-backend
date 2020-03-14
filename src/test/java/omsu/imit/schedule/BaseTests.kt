package omsu.imit.schedule

import omsu.imit.schedule.dto.response.BuildingInfo
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

    fun getFaculty(): Faculty {
        return Faculty(getBuilding(), "ИНСТИТУТ МАТЕМАТИКИ И ИНФОРМАЦИОННЫХ ТЕХНОЛОГИЙ")
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

    fun getUser(enabled: Boolean = true, userRole: UserRole = UserRole.USER): User {
        return User(0,
                "FirstName",
                "Patronymic",
                "LastName",
                "example@gmail.com",
                "password",
                userRole,
                enabled)
    }

    fun getBuildingInfo(building: Building): BuildingInfo {
        return BuildingInfo(building.id, building.number, building.address)
    }
}