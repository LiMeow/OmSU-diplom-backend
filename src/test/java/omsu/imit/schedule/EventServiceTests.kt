package omsu.imit.schedule

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import omsu.imit.schedule.dto.request.CancelEventRequest
import omsu.imit.schedule.dto.request.CreateEventPeriodRequest
import omsu.imit.schedule.dto.request.CreateEventRequest
import omsu.imit.schedule.dto.request.RescheduleEventRequest
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.Interval
import omsu.imit.schedule.repository.EventPeriodRepository
import omsu.imit.schedule.repository.EventRepository
import omsu.imit.schedule.services.ClassroomService
import omsu.imit.schedule.services.EventService
import omsu.imit.schedule.services.LecturerService
import omsu.imit.schedule.services.TimeBlockService
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
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
    fun testCreateEventWithNotIntersectedEventPeriods() {
        val classroom = getClassroom()
        val timeBlock = getTimeBlock()
        val lecturer = getLecturer()
        val event = getEvent()
        val createdEvent = getEvent()
        val eventPeriod = getEventPeriod()
        createdEvent.eventPeriods = mutableListOf(eventPeriod)

        val eventPeriodRequest = CreateEventPeriodRequest(
                classroom.id,
                timeBlock.id,
                eventPeriod.dateFrom,
                eventPeriod.dateTo,
                eventPeriod.interval)
        val request = CreateEventRequest(lecturer.id, true, listOf(eventPeriodRequest))

        every {
            eventPeriodRepository.findByClassroomDayAndTime(
                    eventPeriod.classroom.id,
                    eventPeriod.timeBlock.id,
                    eventPeriod.day,
                    eventPeriod.dateFrom,
                    eventPeriod.dateTo)
        } returns listOf()
        every { timeBlockService.getTimeBlockById(timeBlock.id) } returns timeBlock
        every { classroomService.getClassroomById(classroom.id) } returns classroom
        every { lecturerService.getLecturer(lecturer.id) } returns lecturer
        every { eventRepository.save(event) } returns event
        every { eventPeriodRepository.save(eventPeriod) } returns eventPeriod

        assertEquals(createdEvent, eventService.createEvent(request))

        verify {
            eventPeriodRepository.findByClassroomDayAndTime(
                    eventPeriod.classroom.id,
                    eventPeriod.timeBlock.id,
                    eventPeriod.day,
                    eventPeriod.dateFrom,
                    eventPeriod.dateTo)
        }
        verify { timeBlockService.getTimeBlockById(timeBlock.id) }
        verify { classroomService.getClassroomById(classroom.id) }
        verify { lecturerService.getLecturer(lecturer.id) }
        verify { eventPeriodRepository.save(eventPeriod) }
    }

    @Test
    fun testCreateEventWithIntersectedEventPeriods() {
        val lecturer = getLecturer()
        val event = getEvent()
        val eventPeriod = getEventPeriod()
        val otherEventPeriod = getEventPeriod()

        event.eventPeriods = mutableListOf(eventPeriod)

        val eventPeriodRequest = CreateEventPeriodRequest(
                eventPeriod.classroom.id,
                eventPeriod.timeBlock.id,
                eventPeriod.dateFrom,
                eventPeriod.dateTo,
                eventPeriod.interval)
        val request = CreateEventRequest(lecturer.id, true, listOf(eventPeriodRequest))

        every {
            eventPeriodRepository.findByClassroomDayAndTime(
                    eventPeriod.classroom.id,
                    eventPeriod.timeBlock.id,
                    eventPeriod.day,
                    eventPeriod.dateFrom,
                    eventPeriod.dateTo)
        } returns listOf(otherEventPeriod)
        every { lecturerService.getLecturer(lecturer.id) } returns lecturer

        assertThrows(CommonValidationException::class.java) { eventService.createEvent(request) }

        verify {
            eventPeriodRepository.findByClassroomDayAndTime(
                    eventPeriod.classroom.id,
                    eventPeriod.timeBlock.id,
                    eventPeriod.day,
                    eventPeriod.dateFrom,
                    eventPeriod.dateTo)
        }
        verify { lecturerService.getLecturer(lecturer.id) }
    }

    @Test
    fun testCreateEventAndGetInfo() {
        val classroom = getClassroom()
        val timeBlock = getTimeBlock()
        val lecturer = getLecturer()
        val event = getEvent()
        val createdEvent = getEvent()
        val eventPeriod = getEventPeriod()
        createdEvent.eventPeriods = mutableListOf(eventPeriod)

        val eventPeriodRequest = CreateEventPeriodRequest(
                classroom.id,
                timeBlock.id,
                eventPeriod.dateFrom,
                eventPeriod.dateTo,
                eventPeriod.interval)
        val request = CreateEventRequest(lecturer.id, true, listOf(eventPeriodRequest))
        val response = getEventInfo(createdEvent)

        every {
            eventPeriodRepository.findByClassroomDayAndTime(
                    eventPeriod.classroom.id,
                    eventPeriod.timeBlock.id,
                    eventPeriod.day,
                    eventPeriod.dateFrom,
                    eventPeriod.dateTo)
        } returns listOf()
        every { timeBlockService.getTimeBlockById(timeBlock.id) } returns timeBlock
        every { classroomService.getClassroomById(classroom.id) } returns classroom
        every { lecturerService.getLecturer(lecturer.id) } returns lecturer
        every { eventRepository.save(event) } returns event
        every { eventPeriodRepository.save(eventPeriod) } returns eventPeriod

        assertEquals(response, eventService.createEventAndGetInfo(request))

        verify {
            eventPeriodRepository.findByClassroomDayAndTime(
                    eventPeriod.classroom.id,
                    eventPeriod.timeBlock.id,
                    eventPeriod.day,
                    eventPeriod.dateFrom,
                    eventPeriod.dateTo)
        }
        verify { timeBlockService.getTimeBlockById(timeBlock.id) }
        verify { classroomService.getClassroomById(classroom.id) }
        verify { lecturerService.getLecturer(lecturer.id) }
        verify { eventPeriodRepository.save(eventPeriod) }
    }

    @Test
    fun testGetClassroomsByEvent() {
        val event = getEvent()
        val eventPeriod = getEventPeriod()
        val response = listOf(getClassroomShortInfo(eventPeriod.classroom))
        event.eventPeriods = mutableListOf(eventPeriod)

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

    @Test
    fun testCancelSingleEventPeriodEvent() {
        val event = getEvent()
        val date = LocalDate.of(2020, 5, 15)
        val eventPeriod = getEventPeriod(date, date, Day.FRIDAY, Interval.NONE)
        val request = CancelEventRequest(eventPeriod.id, listOf(date))

        event.eventPeriods = mutableListOf()

        every { eventRepository.existsById(event.id) } returns true
        every { eventRepository.findById(event.id) } returns Optional.of(event)
        every { eventRepository.deleteById(event.id) } returns mockk()
        every { eventPeriodRepository.findById(eventPeriod.id) } returns Optional.of(eventPeriod)
        every { eventPeriodRepository.deleteById(eventPeriod.id) } returns mockk()

        assertEquals(null, eventService.cancelEventOnSomeDates(request))

        verify { eventRepository.existsById(event.id) }
        verify { eventRepository.findById(event.id) }
        verify { eventRepository.deleteById(event.id) }
        verify { eventPeriodRepository.findById(eventPeriod.id) }
        verify { eventPeriodRepository.deleteById(eventPeriod.id) }
    }

    @Test
    fun testMoveStartOfEventPeriod() {
        val event = getEvent()
        val updatedEvent = getEvent()

        val dateFrom = LocalDate.of(2020, 5, 15)
        val updatedDateFrom = LocalDate.of(2020, 5, 22)
        val dateTo = LocalDate.of(2020, 6, 5)

        val eventPeriod = getEventPeriod(dateFrom, dateTo, Day.FRIDAY, Interval.EVERY_WEEK)
        val updatedEventPeriod = getEventPeriod(updatedDateFrom, dateTo, Day.FRIDAY, Interval.EVERY_WEEK)

        event.eventPeriods = mutableListOf(eventPeriod)
        updatedEvent.eventPeriods = mutableListOf(updatedEventPeriod)

        val response = getEventInfo(updatedEvent)
        val request = CancelEventRequest(eventPeriod.id, listOf(dateFrom))

        every { eventRepository.findById(event.id) } returns Optional.of(event)
        every { eventPeriodRepository.findById(eventPeriod.id) } returns Optional.of(eventPeriod)
        every { eventPeriodRepository.save(updatedEventPeriod) } returns updatedEventPeriod

        assertEquals(response, eventService.cancelEventOnSomeDates(request))

        verify { eventRepository.findById(event.id) }
        verify { eventPeriodRepository.findById(eventPeriod.id) }
        verify { eventPeriodRepository.save(updatedEventPeriod) }
    }

    @Test
    fun testMoveEndOfEventPeriod() {
        val event = getEvent()
        val updatedEvent = getEvent()

        val dateFrom = LocalDate.of(2020, 5, 15)
        val dateTo = LocalDate.of(2020, 6, 5)
        val updatedDateTo = LocalDate.of(2020, 5, 29)

        val eventPeriod = getEventPeriod(dateFrom, dateTo, Day.FRIDAY, Interval.EVERY_WEEK)
        val updatedEventPeriod = getEventPeriod(dateFrom, updatedDateTo, Day.FRIDAY, Interval.EVERY_WEEK)

        event.eventPeriods = mutableListOf(eventPeriod)
        updatedEvent.eventPeriods = mutableListOf(updatedEventPeriod)

        val response = getEventInfo(updatedEvent)
        val request = CancelEventRequest(eventPeriod.id, listOf(dateTo))

        every { eventRepository.findById(event.id) } returns Optional.of(event)
        every { eventPeriodRepository.findById(eventPeriod.id) } returns Optional.of(eventPeriod)
        every { eventPeriodRepository.save(updatedEventPeriod) } returns updatedEventPeriod

        assertEquals(response, eventService.cancelEventOnSomeDates(request))

        verify { eventRepository.findById(event.id) }
        verify { eventPeriodRepository.findById(eventPeriod.id) }
        verify { eventPeriodRepository.save(updatedEventPeriod) }
    }

    @Test
    fun testCancelEventPeriodFromMiddleOfEventInterval() {
        val updatedEvent = getEvent()

        val dateFrom = LocalDate.of(2020, 5, 15)
        val dateTo = LocalDate.of(2020, 6, 5)
        val startDateOfNewEventPeriod = LocalDate.of(2020, 5, 29)
        val cancelingDate = LocalDate.of(2020, 5, 22)

        val eventPeriod = getEventPeriod(dateFrom, dateTo, Day.FRIDAY, Interval.EVERY_WEEK)
        val updatedEventPeriod1 = getEventPeriod(dateFrom, dateFrom, Day.FRIDAY, Interval.EVERY_WEEK)
        val updatedEventPeriod2 = getEventPeriod(startDateOfNewEventPeriod, dateTo, Day.FRIDAY, Interval.EVERY_WEEK)

        updatedEvent.eventPeriods = mutableListOf(updatedEventPeriod1, updatedEventPeriod2)

        val response = getEventInfo(updatedEvent)
        val request = CancelEventRequest(eventPeriod.id, listOf(cancelingDate))

        every { eventRepository.findById(updatedEvent.id) } returns Optional.of(updatedEvent)
        every { eventPeriodRepository.findById(eventPeriod.id) } returns Optional.of(eventPeriod)
        every { eventPeriodRepository.save(updatedEventPeriod2) } returns updatedEventPeriod2
        every { eventPeriodRepository.save(updatedEventPeriod1) } returns updatedEventPeriod1

        assertEquals(response, eventService.cancelEventOnSomeDates(request))

        verify { eventRepository.findById(updatedEvent.id) }
        verify { eventPeriodRepository.save(updatedEventPeriod2) }
        verify { eventPeriodRepository.save(updatedEventPeriod1) }
    }

    @Test
    fun testRescheduleEvent() {
        val updatedEvent = getEvent()

        val eventPeriod = getEventPeriod()
        val updatedEventPeriod1 = getEventPeriod()
        val updatedEventPeriod2 = getEventPeriod()


        val timeBlock = getTimeBlock()
        val classroom = getClassroom()
        val updatedDateFrom = LocalDate.of(2020, 5, 8)
        val rescheduleFrom = LocalDate.of(2020, 5, 1)
        val rescheduleTo = LocalDate.of(2020, 5, 4)

        updatedEventPeriod1.dateFrom = rescheduleTo
        updatedEventPeriod1.dateTo = rescheduleTo
        updatedEventPeriod1.day = Day.MONDAY
        updatedEventPeriod1.interval = Interval.NONE
        updatedEventPeriod2.dateFrom = updatedDateFrom
        updatedEvent.eventPeriods = mutableListOf(updatedEventPeriod1, updatedEventPeriod2)

        val response = getEventInfo(updatedEvent)
        val request = RescheduleEventRequest(
                eventPeriod.id,
                classroom.id,
                timeBlock.id,
                rescheduleFrom,
                rescheduleTo)

        every { eventPeriodRepository.findById(eventPeriod.id) } returns Optional.of(eventPeriod)
        every { eventPeriodRepository.save(updatedEventPeriod1) } returns updatedEventPeriod1
        every { eventPeriodRepository.save(updatedEventPeriod2) } returns updatedEventPeriod2
        every { eventRepository.findById(eventPeriod.event.id) } returns Optional.of(updatedEvent)
        every { timeBlockService.getTimeBlockById(timeBlock.id) } returns timeBlock
        every { classroomService.getClassroomById(classroom.id) } returns classroom
        every {
            eventPeriodRepository.findByClassroomDayAndTime(
                    classroom.id,
                    timeBlock.id,
                    Day.MONDAY,
                    rescheduleTo,
                    rescheduleTo)
        } returns listOf()

        assertEquals(response, eventService.rescheduleEventPeriod(request))

        verify { eventPeriodRepository.findById(eventPeriod.id) }
        verify { eventPeriodRepository.save(updatedEventPeriod1) }
        verify { eventRepository.findById(eventPeriod.event.id) }
        verify { timeBlockService.getTimeBlockById(timeBlock.id) }
        verify { classroomService.getClassroomById(classroom.id) }
    }
}