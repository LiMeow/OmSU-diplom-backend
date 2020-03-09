package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateEventPeriodRequest
import omsu.imit.schedule.dto.request.CreateEventRequest
import omsu.imit.schedule.dto.response.ClassroomShortInfo
import omsu.imit.schedule.dto.response.EventInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Event
import omsu.imit.schedule.model.EventPeriod
import omsu.imit.schedule.model.Interval
import omsu.imit.schedule.repository.EventPeriodRepository
import omsu.imit.schedule.repository.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


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
        event.eventPeriods = request.periods.asSequence().map { createEventPeriod(it, event) }.toList();

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

    private fun getEventById(eventId: Int): Event {
        return eventRepository
                .findById(eventId)
                .orElseThrow { NotFoundException(ErrorCode.EVENT_NOT_EXISTS, eventId.toString()) }
    }

    private fun createEventPeriod(request: CreateEventPeriodRequest, event: Event): EventPeriod {
        val timeBlock = timeBlockService.getTimeBlockById(request.timeBlockId)
        val classroom = classroomService.getClassroomById(request.classroomId)

        val eventPeriod = EventPeriod(
                event,
                classroom,
                timeBlock,
                request.day,
                request.dateFrom,
                request.dateTo,
                request.interval)

        eventPeriodRepository.save(eventPeriod);
        return eventPeriod
    }

    private fun checkEventPeriods(eventPeriods: List<CreateEventPeriodRequest>) {
        eventPeriods.forEach { eventPeriod ->

            val existingPeriods = eventPeriodRepository
                    .findByClassroomDayAndTime(
                            eventPeriod.classroomId,
                            eventPeriod.day,
                            eventPeriod.dateFrom,
                            eventPeriod.dateTo,
                            eventPeriod.timeBlockId)
                    .asSequence()
                    .filter {
                        it.interval == eventPeriod.interval || it.interval == Interval.EVERY_WEEK ||
                                (it.interval != Interval.EVERY_WEEK && eventPeriod.interval == Interval.EVERY_WEEK)
                    }
                    .toList()

            if (existingPeriods.isNotEmpty()) {
                throw throw CommonValidationException(ErrorCode.CLASSROOM_ALREADY_BUSY,
                        eventPeriod.classroomId.toString(), eventPeriod.timeBlockId.toString(), eventPeriod.day.name)
            }
        }
    }
}