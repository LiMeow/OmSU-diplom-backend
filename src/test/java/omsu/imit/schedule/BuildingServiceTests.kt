package omsu.imit.schedule

import omsu.imit.schedule.dto.request.CreateBuildingRequest
import omsu.imit.schedule.dto.request.EditBuildingRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.repository.BuildingRepository
import omsu.imit.schedule.service.BuildingService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class BuildingServiceTests {
    @Mock
    lateinit var buildingRepository: BuildingRepository

    private lateinit var buildingService: BuildingService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.buildingService = BuildingService(
                this.buildingRepository)
    }

    @Test
    fun testAddBuilding() {
        val request = CreateBuildingRequest(1, " пр. Мира, 55-а")
        val building = Building(0, request.number, request.address)
        val response = Building(building.id, building.number, building.address)

        `when`(buildingRepository.findByNumberAndAddress(request.number, request.address)).thenReturn(null)
        `when`(buildingRepository.save(building)).thenReturn(building)

        assertEquals(response, buildingService.createBuilding(request))

        verify(buildingRepository).findByNumberAndAddress(request.number, request.address)
        verify(buildingRepository).save(building)
    }

    @Test
    fun testAddAlreadyExistingBuilding() {
        val request = CreateBuildingRequest(1, " пр. Мира, 55-а")
        val building = Building(0, request.number, request.address)

        `when`(buildingRepository.findByNumberAndAddress(request.number, request.address)).thenReturn(building)

        assertThrows(CommonValidationException::class.java) { buildingService.createBuilding(request) }
        verify(buildingRepository).findByNumberAndAddress(request.number, request.address)
    }

    @Test
    fun testGetBuildingById() {
        val building = Building(1, 1, "пр. Мира, 55-а")
        val response = Building(building.id, building.number, building.address)

        `when`(buildingRepository.findById(building.id)).thenReturn(Optional.of(building))
        assertEquals(response, buildingService.getBuildingById(1))

        verify(buildingRepository).findById(1)
    }

    @Test
    fun testGetNonExistingBuildingById() {

        `when`(buildingRepository.findById(1)).thenReturn(Optional.empty())

        assertThrows(NotFoundException::class.java) { buildingService.getBuildingById(1) }
        verify(buildingRepository).findById(1)
    }

    @Test
    fun testEditBuilding() {
        val request = EditBuildingRequest(2, "пр. Мира, 55")
        val building = Building(1, 1, "пр. Мира, 55-а")
        val updatedBuilding = Building(1, request.number!!, request.address!!)
        val response = Building(building.id, updatedBuilding.number, updatedBuilding.address)

        `when`(buildingRepository.findById(building.id)).thenReturn(Optional.of(building))
        `when`(buildingRepository.save(updatedBuilding)).thenReturn(updatedBuilding)

        assertEquals(response, buildingService.editBuilding(building.id, request))

        verify(buildingRepository).findById(building.id)
        verify(buildingRepository).save(updatedBuilding)
    }

    @Test
    fun testEditNonExistingBuilding() {
        val request = EditBuildingRequest(2, "пр. Мира, 55")

        `when`(buildingRepository.findById(2)).thenReturn(Optional.empty())

        assertThrows(NotFoundException::class.java) { buildingService.editBuilding(2, request) }
        verify(buildingRepository).findById(2)
    }

    @Test
    fun testEditBuildingByEmptyRequest() {
        val request = EditBuildingRequest()
        val building = Building(1, 1, "пр. Мира, 55-а")

        `when`(buildingRepository.findById(building.id)).thenReturn(Optional.of(building))

        assertEquals(building, buildingService.editBuilding(building.id, request))
        verify(buildingRepository).findById(building.id)
    }

    @Test
    fun testDeleteBuilding() {

        `when`(buildingRepository.existsById(1)).thenReturn(true)
        buildingService.deleteBuilding(1)

        verify(buildingRepository).existsById(1)
        verify(buildingRepository).deleteById(1)
    }

    @Test
    fun testDeleteNonExistingBuilding() {

        `when`(buildingRepository.existsById(1)).thenReturn(false)

        assertThrows(NotFoundException::class.java) { buildingService.deleteBuilding(1) }
        verify(buildingRepository).existsById(1)
    }
}