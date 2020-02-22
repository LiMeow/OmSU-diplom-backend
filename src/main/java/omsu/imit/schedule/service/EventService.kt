package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateEventRequest
import omsu.imit.schedule.dto.response.EventInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.*
import omsu.imit.schedule.repository.EventRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.sql.Date


@Service
class EventService
@Autowired
constructor(private val classroomService: ClassroomService,
            private val eventRepository: EventRepository,
            private val groupService: GroupService,
            private val lecturerService: LecturerService,
            private val timeBlockService: TimeBlockService) : BaseService() {


    fun createEvent(classroomId: Int, request: CreateEventRequest): EventInfo {
        var groups: List<Group>? = null
        if (request.groupIds!!.isNotEmpty()) groups = groupService.getGroupsByIds(request.groupIds!!)

        val lecturer = lecturerService.getLecturer(request.lecturerId)
        val timeBlock = timeBlockService.getTimeBlockByTime(request.timeFrom, request.timeTo)
        val classroom = classroomService.getClassroomById(classroomId)

        val event = createEvent(
                classroom,
                timeBlock,
                request.day,
                request.dateFrom,
                request.dateTo,
                request.interval,
                lecturer, groups!!,
                request.comment)

        return toEventInfo(event)
    }

    fun createEvent(classroom: Classroom, timeBlock: TimeBlock,
                    day: Day, dateFrom: Date, dateTo: Date, interval: Interval,
                    lecturer: Lecturer, groups: List<Group>?, comment: String? = ""): Event {


        val events = eventRepository.findByClassroomDayAndTime(classroom.id, day, dateFrom, dateTo, timeBlock.id).asSequence()
                .filter {
                    it.interval == interval ||
                            it.interval == Interval.EVERY_WEEK ||
                            (it.interval != Interval.EVERY_WEEK && interval == Interval.EVERY_WEEK)
                }
                .toList()

        if (events.isNotEmpty()) {
            throw throw CommonValidationException(ErrorCode.CLASSROOM_ALREADY_BUSY,
                    classroom.id.toString(), timeBlock.timeFrom, timeBlock.timeTo, day.name)
        }

        val event = Event(classroom, timeBlock, day, dateFrom, dateTo, interval, lecturer, groups!!, comment)
        eventRepository.save(event)
        return event
    }

    fun getEventInfo(eventId: Int): EventInfo {
        return toEventInfo(getEventById(eventId))
    }

//    fun getAuditoryWithOccupationsByDate(auditoryId: Int, date: String): AuditoryInfo {
//        val auditory = auditoryRepository
//                .findById(auditoryId)
//                .orElseThrow { NotFoundException(ErrorCode.AUDITORY_NOT_EXISTS, auditoryId.toString()) }
//
//        auditory.auditoryOccupations = auditoryOccupationRepository.findByAuditoryAndDate(auditoryId, date)
//        return toAuditoryInfo(auditory)
//    }

    fun deleteEvent(eventId: Int) {
        if (!eventRepository.existsById(eventId))
            throw  NotFoundException(ErrorCode.EVENT_NOT_EXISTS, eventId.toString())

        eventRepository.deleteById(eventId)
    }

    fun deleteAllEvents(eventId: Int) {
        eventRepository.deleteAllByClassroom(eventId)
    }

    private fun getEventById(eventId: Int): Event {
        return eventRepository
                .findById(eventId)
                .orElseThrow { NotFoundException(ErrorCode.EVENT_NOT_EXISTS, eventId.toString()) }
    }
}