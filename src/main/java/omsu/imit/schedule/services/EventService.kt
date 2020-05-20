package omsu.imit.schedule.services

import omsu.imit.schedule.dto.request.CancelEventRequest
import omsu.imit.schedule.dto.request.CreateEventPeriodRequest
import omsu.imit.schedule.dto.request.CreateEventRequest
import omsu.imit.schedule.dto.request.RescheduleEventRequest
import omsu.imit.schedule.dto.response.ClassroomShortInfo
import omsu.imit.schedule.dto.response.EventInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.*
import omsu.imit.schedule.repository.EventPeriodRepository
import omsu.imit.schedule.repository.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate


@Service
class EventService
@Autowired
constructor(private val classroomService: ClassroomService,
            private val eventRepository: EventRepository,
            private val eventPeriodRepository: EventPeriodRepository,
            private val lecturerService: LecturerService,
            private val timeBlockService: TimeBlockService) : BaseService() {


    fun createEvent(request: CreateEventRequest): Event {
        val lecturer = lecturerService.getLecturer(request.lecturerId)

        checkEventPeriods(request.periods)

        val event = Event(
                lecturer,
                request.comment,
                request.required)

        eventRepository.save(event)
        event.eventPeriods = request.periods.asSequence().map { createEventPeriod(it, event) }.toMutableList();

        return event
    }

    fun createEventAndGetInfo(request: CreateEventRequest): EventInfo {
        return toEventInfo(createEvent(request))
    }

    fun getClassroomsByEvent(eventId: Int): List<ClassroomShortInfo> {
        val event = getEventById(eventId)

        return event.eventPeriods
                .asSequence()
                .map { toClassroomShortInfo(it.classroom) }
                .toList()
    }

    fun getEventInfo(eventId: Int): EventInfo {
        return toEventInfo(getEventById(eventId))
    }

    fun deleteEvent(eventId: Int) {
        if (!eventRepository.existsById(eventId))
            throw  NotFoundException(ErrorCode.EVENT_NOT_EXISTS, eventId.toString())

        eventRepository.deleteById(eventId)
    }

    fun cancelEventOnSomeDates(request: CancelEventRequest, eventId: Int): EventInfo? {
        var event = getEventById(eventId)
        var result: Event? = null

        request.dates.forEach { date ->
            result = this.cancelEvent(date, event)
        }

        return if (result !== null) toEventInfo(result!!) else null
    }

    fun rescheduleEvent(request: RescheduleEventRequest, eventId: Int): EventInfo {
        val event = getEventById(eventId)
        val timeBlock = timeBlockService.getTimeBlockById(request.timeBlockId)
        val classroom = classroomService.getClassroomById(request.classroomId)
        val day = Day.valueOf(request.to.dayOfWeek.name)

        this.checkEventPeriod(classroom.id, timeBlock.id, day, request.to, request.to, Interval.NONE)
        this.cancelEvent(request.from, event)

        val reschedulingEvent = this.createEventPeriod(event, classroom, timeBlock, day, request.to, request.to, Interval.NONE)
        event.eventPeriods = event.eventPeriods.plus(reschedulingEvent)
        this.eventRepository.save(event)

        return toEventInfo(event)
    }


    private fun cancelEvent(date: LocalDate, event: Event): Event? {
        event.eventPeriods
                .filter { eventPeriod -> eventPeriod.day.description == date.dayOfWeek.toString() }
                .forEach { eventPeriod ->

                    if (eventPeriod.dateFrom.isEqual(date) && eventPeriod.dateTo.isEqual(date)) {
                        /** Cancel a single event */

                        event.eventPeriods = event.eventPeriods.minus(eventPeriod);
                        eventPeriodRepository.deleteById(eventPeriod.id);
                    } else if (eventPeriod.dateFrom.isEqual(date)) {
                        /** Move Start Date */

                        eventPeriod.dateFrom = date.plusWeeks(1)
                        eventPeriodRepository.save(eventPeriod)

                    } else if (eventPeriod.dateTo.isEqual(date)) {
                        /** Move End Date */

                        eventPeriod.dateTo = date.minusWeeks(1)
                        eventPeriodRepository.save(eventPeriod)

                    } else if (eventPeriod.dateFrom.isBefore(date) && eventPeriod.dateTo.isAfter(date)) {
                        /** Cancel the event in the middle of the interval */

                        val newEventPeriod = createEventPeriod(
                                eventPeriod.event, eventPeriod.classroom,
                                eventPeriod.timeBlock, eventPeriod.day,
                                date.plusWeeks(1), eventPeriod.dateTo,
                                eventPeriod.interval)
                        eventPeriodRepository.save(newEventPeriod)

                        eventPeriod.dateTo = date.minusWeeks(1)
                        eventPeriodRepository.save(eventPeriod)
                        event.eventPeriods = event.eventPeriods.plus(newEventPeriod)
                    }
                }

        when {
            event.eventPeriods.isEmpty() -> {
                this.eventRepository.deleteById(event.id)
            }
            else -> {
                this.eventRepository.save(event)
                println(event)
            }
        }

        return if (event.eventPeriods.isNotEmpty()) event else null
    }

    private fun getEventById(eventId: Int): Event {
        return eventRepository
                .findById(eventId)
                .orElseThrow { NotFoundException(ErrorCode.EVENT_NOT_EXISTS, eventId.toString()) }
    }

    private fun createEventPeriod(event: Event, classroom: Classroom, timeBlock: TimeBlock, day: Day,
                                  dateFrom: LocalDate, dateTo: LocalDate, interval: Interval): EventPeriod {

        val eventPeriod = EventPeriod(event, classroom, timeBlock, day, dateFrom, dateTo, interval)
        eventPeriodRepository.save(eventPeriod);
        return eventPeriod
    }


    private fun createEventPeriod(request: CreateEventPeriodRequest, event: Event): EventPeriod {
        val timeBlock = timeBlockService.getTimeBlockById(request.timeBlockId)
        val classroom = classroomService.getClassroomById(request.classroomId)

        return createEventPeriod(event, classroom, timeBlock, request.day, request.dateFrom, request.dateTo, request.interval)
    }

    private fun checkEventPeriod(classroomId: Int, timeBlockId: Int, day: Day, dateFrom: LocalDate, dateTo: LocalDate, interval: Interval) {
        val existingPeriods = eventPeriodRepository
                .findByClassroomDayAndTime(classroomId, timeBlockId, day, dateFrom, dateTo)
                .asSequence()
                .filter {
                    it.interval == interval ||
                            it.interval == Interval.EVERY_WEEK ||
                            (it.interval != Interval.EVERY_WEEK && interval == Interval.EVERY_WEEK)
                }
                .toList()

        if (existingPeriods.isNotEmpty()) {
            throw throw CommonValidationException(ErrorCode.CLASSROOM_ALREADY_BUSY, classroomId.toString(), timeBlockId.toString(), day.name)

        }
    }

    private fun checkEventPeriods(eventPeriods: List<CreateEventPeriodRequest>) {
        eventPeriods.forEach { eventPeriod ->
            this.checkEventPeriod(
                    eventPeriod.classroomId,
                    eventPeriod.timeBlockId,
                    eventPeriod.day,
                    eventPeriod.dateFrom,
                    eventPeriod.dateTo,
                    eventPeriod.interval)
        }
    }
}