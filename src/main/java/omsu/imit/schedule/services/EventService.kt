package omsu.imit.schedule.services

import omsu.imit.schedule.dto.request.*
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

        this.checkEventPeriods(request.periods)

        val event = Event(
                lecturer,
                request.comment,
                request.required)

        eventRepository.save(event)
        event.eventPeriods = request.periods
                .asSequence()
                .map { this.createEventPeriod(it, event) }
                .toMutableList();

        return event
    }

    fun editEvent(eventId: Int, request: EditEventRequest): Event {
        val event = getEventById(eventId)

        if (request.lecturerId !== null) {
            event.lecturer = lecturerService.getLecturer(request.lecturerId!!)
        }
        if (request.required !== null) {
            event.required = request.required!!
        }
        if (request.comment !== null) {
            event.comment = request.comment!!
        }
        if (request.periods !== null) {
            val updatedEventPeriods = request.periods!!.asSequence().map { editEventPeriod(event, it) }.toList()

            event.eventPeriods.asSequence().forEachIndexed { index, eventPeriod ->
                updatedEventPeriods.asSequence().forEach { updatedEventPeriod ->
                    if (eventPeriod.id == updatedEventPeriod.id) {
                        event.eventPeriods[index] = updatedEventPeriod
                    }
                }
            }
        }

        eventRepository.save(event)
        return event
    }

    fun editEventAndGetInfo(eventId: Int, request: EditEventRequest): EventInfo {
        return toEventInfo(editEvent(eventId, request))
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

    fun cancelEventOnSomeDates(request: CancelEventRequest): EventInfo? {
        val eventPeriod = getEventPeriodById(request.eventPeriodId)

        request.dates.forEach { date ->
            this.cancelEvent(date, eventPeriod)
        }

        val event = getEventById(eventPeriod.event.id)

        if (event.eventPeriods.isEmpty()) {
            deleteEvent(event.id)
            return null
        }
        return toEventInfo(event)
    }

    fun rescheduleEventPeriod(request: RescheduleEventRequest): EventInfo {
        val eventPeriod = getEventPeriodById(request.eventPeriodId)
        val timeBlock = timeBlockService.getTimeBlockById(request.newTimeBlockId)
        val classroom = classroomService.getClassroomById(request.newClassroomId)
        val day = Day.valueOf(request.rescheduleTo.dayOfWeek.name)

        if (eventPeriod.day.description !== request.rescheduleFrom.dayOfWeek.toString()) {
            throw CommonValidationException(ErrorCode.DATE_NOT_INCLUDING_IN_EVENT_PERIOD, request.rescheduleFrom.toString())
        }

        this.checkEventPeriod(
                classroom.id,
                timeBlock.id,
                day,
                request.rescheduleTo,
                request.rescheduleTo,
                Interval.NONE)

        this.cancelEvent(request.rescheduleFrom, eventPeriod)

        val rescheduledEventPeriod = this.createEventPeriod(
                eventPeriod.event,
                classroom,
                timeBlock,
                day,
                request.rescheduleTo,
                request.rescheduleTo,
                Interval.NONE
        )

        this.eventPeriodRepository.save(rescheduledEventPeriod)
        return getEventInfo(eventPeriod.event.id)
    }

    private fun cancelEvent(date: LocalDate, eventPeriod: EventPeriod) {

        if (eventPeriod.dateFrom.isEqual(date) && eventPeriod.dateTo.isEqual(date)) {
            /** Cancel a single event */

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

            val newEventPeriod = this.createEventPeriod(
                    eventPeriod.event,
                    eventPeriod.classroom,
                    eventPeriod.timeBlock,
                    eventPeriod.day,
                    date.plusWeeks(1),
                    eventPeriod.dateTo,
                    eventPeriod.interval)

            eventPeriod.dateTo = date.minusWeeks(1)
            eventPeriodRepository.save(eventPeriod)
            eventPeriodRepository.save(newEventPeriod)
        }
    }


    private fun editEventPeriod(event: Event, request: EditEventPeriodRequest): EventPeriod {
        val eventPeriod = this.getEventPeriodById(request.eventPeriodId)

        if (eventPeriod.event.id != event.id) {
            throw CommonValidationException(ErrorCode.EVENT_NOT_CONTAIN_PERIOD, event.id.toString(), eventPeriod.id.toString())
        }

        if (request.classroomId !== null) {
            eventPeriod.classroom = this.classroomService.getClassroomById(request.classroomId!!)
        }
        if (request.timeBlockId !== null) {
            eventPeriod.timeBlock = this.timeBlockService.getTimeBlockById(request.timeBlockId!!)
        }
        if (request.dateFrom !== null) {
            eventPeriod.dateFrom = request.dateFrom!!
            eventPeriod.day = Day.valueOf(request.dateFrom!!.dayOfWeek.name)
        }
        if (request.dateTo !== null) {
            eventPeriod.dateTo = request.dateTo!!
        }
        if (request.interval !== null) {
            eventPeriod.interval = request.interval!!
        }
        this.checkEventPeriod(
                classroomId = eventPeriod.classroom.id,
                timeBlockId = eventPeriod.timeBlock.id,
                day = eventPeriod.day,
                dateFrom = eventPeriod.dateFrom,
                dateTo = eventPeriod.dateTo,
                interval = eventPeriod.interval,
                eventPeriodId = eventPeriod.id)
        this.eventPeriodRepository.save(eventPeriod)

        return eventPeriod
    }

    private fun getEventById(id: Int): Event {
        return eventRepository
                .findById(id)
                .orElseThrow { NotFoundException(ErrorCode.EVENT_NOT_EXISTS, id.toString()) }
    }

    private fun getEventPeriodById(id: Int): EventPeriod {
        return eventPeriodRepository
                .findById(id)
                .orElseThrow { NotFoundException(ErrorCode.EVENT_PERIOD_NOT_EXISTS, id.toString()) }
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
        val day = Day.valueOf(request.dateFrom.dayOfWeek.name)

        return createEventPeriod(
                event,
                classroom,
                timeBlock,
                day,
                request.dateFrom,
                request.dateTo,
                request.interval)
    }

    private fun checkEventPeriod(classroomId: Int, timeBlockId: Int, day: Day, dateFrom: LocalDate, dateTo: LocalDate, interval: Interval, eventPeriodId: Int? = null) {
        val existingPeriods = eventPeriodRepository
                .findByClassroomDayAndTime(classroomId, timeBlockId, day, dateFrom, dateTo)
                .asSequence()
                .filter {
                    (eventPeriodId !== null && it.id != eventPeriodId) ||
                            (it.interval == interval ||
                                    it.interval == Interval.EVERY_WEEK ||
                                    (it.interval != Interval.EVERY_WEEK && interval == Interval.EVERY_WEEK))
                }
                .toList()

        if (existingPeriods.isNotEmpty()) {
            throw throw CommonValidationException(
                    ErrorCode.CLASSROOM_ALREADY_BUSY,
                    classroomId.toString(),
                    timeBlockId.toString(),
                    day.name)
        }
    }

    private fun checkEventPeriods(eventPeriods: List<CreateEventPeriodRequest>) {
        eventPeriods.forEach { eventPeriod ->
            this.checkEventPeriod(
                    eventPeriod.classroomId,
                    eventPeriod.timeBlockId,
                    Day.valueOf(eventPeriod.dateFrom.dayOfWeek.name),
                    eventPeriod.dateFrom,
                    eventPeriod.dateTo,
                    eventPeriod.interval)
        }
    }
}