package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
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
        val building = Building(request.number, request.address)
        buildingRepository.save(building)

        return building
    }

    fun getBuildingById(buildingId: Int): Building {
        return buildingRepository
                .findById(buildingId)
                .orElseThrow {
                    NotFoundException(ErrorCode.BUILDING_NOT_EXISTS, buildingId.toString())
                }
    }

    fun editBuilding(buildingId: Int, request: EditBuildingRequest): Building {
        val building = getBuildingById(buildingId)

        if (request.number != 0) building.number = request.number!!
        if (!request.address.isNullOrEmpty()) building.address = request.address!!

        buildingRepository.save(building)
        return building
    }

    fun deleteBuilding(buildingId: Int) {
        if (!buildingRepository.existsById(buildingId))
            throw  NotFoundException(ErrorCode.BUILDING_NOT_EXISTS, buildingId.toString())

        buildingRepository.deleteById(buildingId)
    }
}
