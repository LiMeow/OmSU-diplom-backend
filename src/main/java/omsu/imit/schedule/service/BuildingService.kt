package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.repository.BuildingRepository
import omsu.imit.schedule.requests.CreateBuildingRequest
import omsu.imit.schedule.requests.EditBuildingRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class BuildingService @Autowired
constructor(private val buildingRepository: BuildingRepository) {


    fun addBuilding(request: CreateBuildingRequest): Building {
        if (buildingRepository.findByNumberAndAddress(request.number, request.address) != null)
            throw ScheduleGeneratorException(ErrorCode.BUILDING_ALREADY_EXISTS, request.number.toString(), request.address)

        val building = Building(request.number, request.address)
        buildingRepository.save(building)

        return building
    }

    fun getBuildingById(buildingId: Int): Building {
        return buildingRepository.findById(buildingId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.BUILDING_NOT_EXISTS, buildingId.toString())
    }

    fun editBuilding(buildingId: Int, request: EditBuildingRequest): Building {
        val building = buildingRepository.findById(buildingId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.BUILDING_NOT_EXISTS, buildingId.toString())

        if (request.number == 0 && request.address.isNullOrEmpty())
            throw ScheduleGeneratorException(ErrorCode.EMPTY_REQUEST_BODY)
        if (request.number != 0) building.number = request.number!!
        if (!request.address.isNullOrEmpty()) building.address = request.address!!

        buildingRepository.save(building)
        return building
    }

    fun deleteBuilding(buildingId: Int) {
        if (!buildingRepository.existsById(buildingId))
            throw ScheduleGeneratorException(ErrorCode.BUILDING_NOT_EXISTS, buildingId.toString())

        buildingRepository.deleteById(buildingId)
    }
}
