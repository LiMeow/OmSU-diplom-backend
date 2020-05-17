package omsu.imit.schedule.service

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.CreateEventRequest
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.repository.EventPeriodRepository
import omsu.imit.schedule.repository.EventRepository
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class EventServiceTests : BaseTests() {
    @MockK
    lateinit var classroomService: ClassroomService

    @MockK
    lateinit var eventRepository: EventRepository

    @MockK
    lateinit var eventPeriodRepository: EventPeriodRepository

    @MockK
    lateinit var lecturerService: LecturerService

    @MockK
    lateinit var timeBlockService: TimeBlockService

    private lateinit var eventService: EventService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.eventService = EventService(
                classroomService,
                eventRepository,
                eventPeriodRepository,
                lecturerService,
                timeBlockService)
    }

    @Test
    fun testCreateEvent() {
        val lecturer = getLecturer()
        val event = getEvent()
        val request = CreateEventRequest(lecturer.id, true)

        every { eventRepository.save(event) } returns event
        every { lecturerService.getLecturer(lecturer.id) } returns lecturer

        assertEquals(event, eventService.createEvent(request))

        verify { lecturerService.getLecturer(lecturer.id) }
        verify { eventRepository.save(event) }
    }

    @Test
    fun testCreateEventAndGetInfo() {
        val lecturer = getLecturer()
        val event = getEvent()
        val request = CreateEventRequest(lecturer.id, true)
        val response = getEventInfo(event)

        every { eventRepository.save(event) } returns event
        every { lecturerService.getLecturer(lecturer.id) } returns lecturer

        assertEquals(response, eventService.createEventAndGetInfo(request))

        verify { lecturerService.getLecturer(lecturer.id) }
        verify { eventRepository.save(event) }
    }

    @Test
    fun testGetClassroomsByEvent() {
        val event = getEvent()
        val eventPeriod = getEventPeriod()
        val response = listOf(getClassroomShortInfo(eventPeriod.classroom))
        event.eventPeriods = listOf(eventPeriod)

        every { eventRepository.findById(event.id) } returns Optional.of(event)
        assertEquals(response, eventService.getClassroomsByEvent(event.id))
        verify { eventRepository.findById(event.id) }
    }

    @Test
    fun testGetEventInfo() {
        val event = getEvent()
        val response = getEventInfo(event)

        every { eventRepository.findById(event.id) } returns Optional.of(event)
        assertEquals(response, eventService.getEventInfo(event.id))
        verify { eventRepository.findById(event.id) }
    }

    @Test
    fun testDeleteEvent() {
        val id = 1

        every { eventRepository.existsById(id) } returns true
        every { eventRepository.deleteById(id) } returns mockk()

        eventService.deleteEvent(id)

        verify { eventRepository.existsById(id) }
        verify { eventRepository.deleteById(id) }
    }

    @Test
    fun testDeleteEventByNonExistingId() {
        val id = 1

        every { eventRepository.existsById(id) } returns false
        every { eventRepository.deleteById(id) } returns mockk()

        assertThrows(NotFoundException::class.java) { eventService.deleteEvent(id) }
        verify { eventRepository.existsById(id) }
    }


}