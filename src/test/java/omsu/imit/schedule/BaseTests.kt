package omsu.imit.schedule

import omsu.imit.schedule.dto.response.BuildingInfo
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.model.Chair
import omsu.imit.schedule.model.Faculty

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

    fun getBuildingInfo(building: Building): BuildingInfo {
        return BuildingInfo(building.id, building.number, building.address)
    }
}