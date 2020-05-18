package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateEventPeriodRequest
import omsu.imit.schedule.dto.request.CreateEventRequest
import omsu.imit.schedule.dto.request.CreateScheduleItemRequest
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.ActivityType
import omsu.imit.schedule.repository.ScheduleItemRepository
import omsu.imit.schedule.services.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class ScheduleItemServiceTests : BaseTests() {

    @MockK
    lateinit var eventService: EventService

    @MockK
    lateinit var disciplineService: DisciplineService

    @MockK
    lateinit var groupService: GroupService

    @MockK
    lateinit var scheduleService: ScheduleService

    @MockK
    lateinit var scheduleItemRepository: ScheduleItemRepository

    private lateinit var scheduleItemService: ScheduleItemService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.scheduleItemService = ScheduleItemService(
                this.eventService,
                this.disciplineService,
                this.groupService,
                this.scheduleService,
                this.scheduleItemRepository)
    }

    @Test
    fun testCreateScheduleItem() {
        val eventPeriod = getEventPeriod()
        val event = getEvent()
        val schedule = getSchedule()
        val scheduleItem = getScheduleItem()
        val discipline = getDiscipline()
        val group = getGroup()
        val groups = listOf(group)

        val eventPeriodRequest = CreateEventPeriodRequest(
                0,
                0,
                eventPeriod.day,
                eventPeriod.dateFrom,
                eventPeriod.dateTo,
                eventPeriod.interval)
        val eventRequest = CreateEventRequest(0, true, listOf(eventPeriodRequest))
        val request = CreateScheduleItemRequest(eventRequest, ActivityType.PRACTICE, discipline.id, listOf(group.id))

        every { eventService.createEvent(request.event) } returns event
        every { groupService.getGroupsByIds(listOf(group.id)) } returns groups
        every { scheduleService.getScheduleById(schedule.id) } returns schedule
        every { scheduleItemRepository.save(scheduleItem) } returns scheduleItem
        every { disciplineService.getDisciplineById(discipline.id) } returns discipline

        scheduleItemService.createScheduleItem(schedule.id, request)

        verify { eventService.createEvent(request.event) }
        verify { groupService.getGroupsByIds(listOf(group.id)) }
        verify { scheduleService.getScheduleById(schedule.id) }
        verify { scheduleItemRepository.save(scheduleItem) }
        verify { disciplineService.getDisciplineById(discipline.id) }

    }

    @Test
    fun testGetScheduleItemById() {
        val scheduleItem = getScheduleItem()

        every { scheduleItemRepository.findById(scheduleItem.id) } returns Optional.of(scheduleItem)
        assertEquals(scheduleItem, scheduleItemService.getScheduleItemById(scheduleItem.id))
        verify { scheduleItemRepository.findById(scheduleItem.id) }
    }

    @Test
    fun testGetScheduleItemByNonExistingId() {
        val id = 1

        every { scheduleItemRepository.findById(id) } returns Optional.empty()
        assertThrows(NotFoundException::class.java) { scheduleItemService.getScheduleItemById(id) }
        verify { scheduleItemRepository.findById(id) }
    }

    @Test
    fun testGetScheduleItemInfo() {
        val scheduleItem = getScheduleItem()

        every { scheduleItemRepository.findById(scheduleItem.id) } returns Optional.of(scheduleItem)
        scheduleItemService.getScheduleItemInfo(scheduleItem.id)
        verify { scheduleItemRepository.findById(scheduleItem.id) }
    }
}