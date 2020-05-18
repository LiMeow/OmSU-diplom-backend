package omsu.imit.schedule.services

import omsu.imit.schedule.dto.request.CreateBuildingRequest
import omsu.imit.schedule.dto.request.EditBuildingRequest
import omsu.imit.schedule.dto.response.BuildingInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.repository.BuildingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
open class BuildingService @Autowired
constructor(private val buildingRepository: BuildingRepository) : BaseService() {


    fun createBuilding(request: CreateBuildingRequest): BuildingInfo {
        if (buildingRepository.findByNumberAndAddress(request.number, request.address) != null)
            throw CommonValidationException(ErrorCode.BUILDING_ALREADY_EXISTS, request.number.toString(), request.address);

        val building = Building(request.number, request.address)
        buildingRepository.save(building)

        return toBuildingInfo(building);
    }

    fun getBuildingById(buildingId: Int): Building {
        return buildingRepository
                .findById(buildingId)
                .orElseThrow { NotFoundException(ErrorCode.BUILDING_NOT_EXISTS, buildingId.toString()) }
    }

    fun getAllBuildings(): List<BuildingInfo> {
        return buildingRepository.findAll().asSequence().map { toBuildingInfo(it) }.toList()
    }

    fun editBuilding(buildingId: Int, request: EditBuildingRequest): BuildingInfo {
        val building = getBuildingById(buildingId)

        if (request.number != 0) building.number = request.number!!
        if (!request.address.isNullOrEmpty()) building.address = request.address!!

        buildingRepository.save(building)
        return toBuildingInfo(building)
    }

    fun deleteBuilding(buildingId: Int) {
        if (!buildingRepository.existsById(buildingId))
            throw  NotFoundException(ErrorCode.BUILDING_NOT_EXISTS, buildingId.toString())

        buildingRepository.deleteById(buildingId)
    }

    fun getBuildingInfo(buildingId: Int): BuildingInfo {
        return toBuildingInfo(getBuildingById(buildingId))
    }
}
