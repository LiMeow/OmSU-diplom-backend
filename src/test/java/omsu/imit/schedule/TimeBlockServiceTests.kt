package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateTimeBlockRequest
import omsu.imit.schedule.dto.request.EditTimeBlockRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.TimeBlock
import omsu.imit.schedule.repository.TimeBlockRepository
import omsu.imit.schedule.service.TimeBlockService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.dao.DataIntegrityViolationException
import java.util.*

@ExtendWith(MockKExtension::class)
class TimeBlockServiceTests : BaseTests() {

    @MockK
    lateinit var timeBlockRepository: TimeBlockRepository

    private lateinit var timeBlockService: TimeBlockService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.timeBlockService = TimeBlockService(timeBlockRepository)
    }

    @Test
    fun testCreateTimeBlock() {
        val timeBlock = getTimeBlock()
        val request = CreateTimeBlockRequest(timeBlock.timeFrom, timeBlock.timeTo)

        every { timeBlockRepository.save(timeBlock) } returns (timeBlock)

        assertEquals(timeBlock, timeBlockService.createTimeBlock(request))
        verify { timeBlockRepository.save(timeBlock) }
    }

    @Test
    fun testCreateAlreadyExistingTimeBlock() {
        val timeBlock = getTimeBlock()
        val request = CreateTimeBlockRequest(timeBlock.timeFrom, timeBlock.timeTo)

        every { timeBlockRepository.save(timeBlock) } throws DataIntegrityViolationException("")

        assertThrows(CommonValidationException::class.java) { timeBlockService.createTimeBlock(request) }
        verify { timeBlockRepository.save(timeBlock) }
    }

    @Test
    fun testGetTimeBlockById() {
        val timeBlock = getTimeBlock()

        every { timeBlockRepository.findById(timeBlock.id) } returns Optional.of(timeBlock)

        assertEquals(timeBlock, timeBlockService.getTimeBlockById(timeBlock.id))
        verify { timeBlockRepository.findById(timeBlock.id) }
    }

    @Test
    fun testGetNonExistingTimeBlockById() {
        val id = 1;

        every { timeBlockRepository.findById(id) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { timeBlockService.getTimeBlockById(id) }
        verify { timeBlockRepository.findById(id) }
    }

    @Test
    fun testGetAllTimeBlocks() {
        val timeBlock1 = TimeBlock("8:00", "9:35")
        val timeBlock2 = TimeBlock("9:45", "11:20")
        val timeBlocks = mutableListOf(timeBlock1, timeBlock2)

        every { timeBlockRepository.findAll() } returns timeBlocks

        assertEquals(timeBlocks, timeBlockService.getAllTimeBlocks())
        verify { timeBlockRepository.findAll() }
    }

    @Test
    fun testEditTimeBlock() {
        val timeBlock = TimeBlock(1, "8:00", "9:35")
        val updatedTimeBlock = TimeBlock(1, "9:45", "11:20")

        val request = EditTimeBlockRequest(updatedTimeBlock.timeFrom, updatedTimeBlock.timeTo)

        every { timeBlockRepository.findById(timeBlock.id) } returns Optional.of(timeBlock)
        every { timeBlockRepository.save(updatedTimeBlock) } returns (updatedTimeBlock)

        assertEquals(updatedTimeBlock, timeBlockService.editTimeBlock(timeBlock.id, request))
        verify { timeBlockRepository.findById(timeBlock.id) }
        verify { timeBlockRepository.save(updatedTimeBlock) }
    }

    @Test
    fun testEditTimeBlockByEmptyRequest() {
        val timeBlock = TimeBlock(1, "8:00", "9:35")
        val request = EditTimeBlockRequest("", "")

        every { timeBlockRepository.findById(timeBlock.id) } returns Optional.of(timeBlock)
        every { timeBlockRepository.save(timeBlock) } returns (timeBlock)

        assertEquals(timeBlock, timeBlockService.editTimeBlock(timeBlock.id, request))
        verify { timeBlockRepository.findById(timeBlock.id) }
        verify { timeBlockRepository.save(timeBlock) }
    }

    @Test
    fun testEditNonExistingTimeBlock() {
        val id = 1
        val request = EditTimeBlockRequest("9:45", "11:20")

        every { timeBlockRepository.findById(id) } returns Optional.empty()

        assertThrows(NotFoundException::class.java) { timeBlockService.editTimeBlock(id, request) }
        verify { timeBlockRepository.findById(id) }
    }

    @Test
    fun testDeleteTimeBlockById() {
        val id = 1;

        every { timeBlockRepository.existsById(id) } returns true
        every { timeBlockRepository.deleteById(id) } returns mockk()

        timeBlockService.deleteTimeBlock(id)

        verify { timeBlockRepository.existsById(id) }
        verify { timeBlockRepository.deleteById(id) }
    }

    @Test
    fun testDeleteNonExistingTimeBlock() {
        val id = 1;
        every { timeBlockRepository.existsById(id) } returns false

        assertThrows(NotFoundException::class.java) { timeBlockService.deleteTimeBlock(id) }
        verify { timeBlockRepository.existsById(id) }
    }

    @Test
    fun testDeleteAllTimeBlocks() {
        every { timeBlockRepository.deleteAll() } returns mockk()

        timeBlockService.deleteAllTimeBlocks()
        verify { timeBlockRepository.deleteAll() }
    }
}