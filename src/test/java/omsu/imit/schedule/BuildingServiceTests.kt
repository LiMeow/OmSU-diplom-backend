package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateBuildingRequest
import omsu.imit.schedule.dto.request.EditBuildingRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.repository.BuildingRepository
import omsu.imit.schedule.services.BuildingService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*


@ExtendWith(MockKExtension::class)
class BuildingServiceTests : BaseTests() {
    @MockK
    lateinit var buildingRepository: BuildingRepository

    private lateinit var buildingService: BuildingService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.buildingService = BuildingService(this.buildingRepository)
    }

    @Test
    fun testCreateBuilding() {
        val building = getBuilding()
        val request = CreateBuildingRequest(building.number, building.address)
        val response = getBuildingInfo(building)

        every { buildingRepository.findByNumberAndAddress(request.number, request.address) } returns (null)
        every { buildingRepository.save(building) } returns building

        assertEquals(response, buildingService.createBuilding(request))

        verify { buildingRepository.findByNumberAndAddress(request.number, request.address) }
        verify { buildingRepository.save(building) }
    }

    @Test
    fun testCreateAlreadyExistingBuilding() {
        val building = getBuilding()
        val request = CreateBuildingRequest(building.number, building.address)

        every { buildingRepository.findByNumberAndAddress(request.number, request.address) } returns building

        assertThrows(CommonValidationException::class.java) { buildingService.createBuilding(request) }

        verify { buildingRepository.findByNumberAndAddress(request.number, request.address) }
    }

    @Test
    fun testGetBuildingById() {
        val building = getBuilding()

        every { buildingRepository.findById(building.id) } returns Optional.of(building)
        assertEquals(building, buildingService.getBuildingById(building.id))

        verify { buildingRepository.findById(building.id) }
    }

    @Test
    fun testGetNonExistingBuildingById() {
        every { buildingRepository.findById(1) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { buildingService.getBuildingById(1) }
        verify { buildingRepository.findById(1) }
    }

    @Test
    fun testGetAllBuildings() {
        val building1 = Building(1, 1, "пр. Мира, 55-а")
        val building2 = Building(2, 2, "пр. Мира, 55")
        val buildings = listOf(building1, building2)
        val response = buildings.asSequence().map { getBuildingInfo(it) }.toList()

        every { buildingRepository.findAll() } returns buildings
        assertEquals(response, buildingService.getAllBuildings())

        verify { buildingRepository.findAll() }
    }

    @Test
    fun testEditBuilding() {
        val building = getBuilding()
        val updatedBuilding = Building(building.id, 2, "пр. Мира, 55")

        val request = EditBuildingRequest(updatedBuilding.number, updatedBuilding.address)
        val response = getBuildingInfo(updatedBuilding)


        every { buildingRepository.findById(building.id) } returns Optional.of(building)
        every { buildingRepository.save(updatedBuilding) } returns updatedBuilding

        assertEquals(response, buildingService.editBuilding(building.id, request))

        verify { buildingRepository.findById(building.id) }
        verify { buildingRepository.save(updatedBuilding) }
    }

    @Test
    fun testEditNonExistingBuilding() {
        val buildingId = 1;
        val request = EditBuildingRequest(2, "пр. Мира, 55")

        every { buildingRepository.findById(buildingId) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { buildingService.editBuilding(buildingId, request) }
        verify { buildingRepository.findById(1) }
    }

    @Test
    fun testEditBuildingByEmptyRequest() {
        val building = getBuilding()
        val request = EditBuildingRequest()
        val response = getBuildingInfo(building)

        every { buildingRepository.findById(building.id) } returns Optional.of(building)
        every { buildingRepository.save(building) } returns building

        assertEquals(response, buildingService.editBuilding(building.id, request))
        verify { buildingRepository.findById(building.id) }
    }

    @Test
    fun testDeleteBuilding() {
        val id = 1;

        every { buildingRepository.existsById(id) } returns true
        every { buildingRepository.deleteById(id) } returns mockk()

        buildingService.deleteBuilding(id)

        verify { buildingRepository.existsById(id) }
        verify { buildingRepository.deleteById(id) }
    }

    @Test
    fun testDeleteNonExistingBuilding() {
        every { buildingRepository.existsById(1) } returns false

        assertThrows(NotFoundException::class.java) { buildingService.deleteBuilding(1) }
        verify { buildingRepository.existsById(1) }
    }

    @Test
    fun testGetBuildingInfo() {
        val building = getBuilding()
        val response = getBuildingInfo(building)

        every { buildingRepository.findById(building.id) } returns Optional.of(building)
        assertEquals(response, buildingService.getBuildingInfo(building.id))

        verify { buildingRepository.findById(building.id) }
    }
}