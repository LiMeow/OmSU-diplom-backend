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

    fun getFaculty(): Faculty {
        return Faculty(getBuilding(), "ИНСТИТУТ МАТЕМАТИКИ И ИНФОРМАЦИОННЫХ ТЕХНОЛОГИЙ")
    }

    fun getTag(): Tag {
        return Tag("tag")
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