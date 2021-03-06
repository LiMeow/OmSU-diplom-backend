package omsu.imit.schedule.services

import omsu.imit.schedule.dto.request.CreateClassroomRequest
import omsu.imit.schedule.dto.request.EditClassroomRequest
import omsu.imit.schedule.dto.response.*
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.model.Classroom
import omsu.imit.schedule.model.EventPeriod
import omsu.imit.schedule.repository.ClassroomRepository
import omsu.imit.schedule.repository.EventPeriodRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

@Service
class ClassroomService
@Autowired
constructor(private val buildingService: BuildingService,
            private val classroomRepository: ClassroomRepository,
            private val eventPeriodRepository: EventPeriodRepository,
            private val tagService: TagService) : BaseService() {

    fun createClassroom(request: CreateClassroomRequest): ClassroomShortInfo {
        val building = buildingService.getBuildingById(request.buildingId);
        val classroom = Classroom(building, request.number)

        if (!request.tags.isNullOrEmpty())
            classroom.tags = tagService.getAllTagsByIds(request.tags!!)

        try {
            classroomRepository.save(classroom)
        } catch (e: DataIntegrityViolationException) {
            throw CommonValidationException(ErrorCode.CLASSROOM_ALREADY_EXISTS, request.number, request.buildingId.toString())
        }

        return toClassroomShortInfo(classroom)
    }

    fun getClassroomById(classroomId: Int): Classroom {
        return classroomRepository
                .findById(classroomId)
                .orElseThrow { NotFoundException(ErrorCode.CLASSROOM_NOT_EXISTS, classroomId.toString()) }
    }

    fun getClassroomsByTags(tags: List<Int>): List<ClassroomShortInfo> {
        return classroomRepository.findAllByTags(tags).asSequence().map { toClassroomShortInfo(it) }.toList();
    }

    fun getAllClassroomsByBuilding(buildingId: Int, page: Int, size: Int): ClassroomsByBuildingInfo {
        val building: Building = buildingService.getBuildingById(buildingId);
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("number"))

        val classrooms = classroomRepository
                .findAllByBuilding(buildingId, pageable)
                .asSequence()
                .map { toClassroomShortInfo(it) }
                .toList()
        return ClassroomsByBuildingInfo(createMetaInfo(building, page, size), classrooms)
    }

    fun getClassroomWithEventsByDate(classroomId: Int, requestDateFrom: LocalDate, requestDateTo: LocalDate): ClassroomInfoByDate {
        val classroom = getClassroomById(classroomId)
        val dateFrom = LocalDate.parse(requestDateFrom.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val dateTo = LocalDate.parse(requestDateTo.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val events = eventPeriodRepository.findAllByClassroomDayAndDate(classroomId, dateFrom, dateTo)
        return createClassroomInfoByDate(dateFrom, dateTo, classroom, events)
    }

    fun editClassroom(classroomId: Int, request: EditClassroomRequest): ClassroomShortInfo {
        val classroom = getClassroomById(classroomId)

        if (!request.number.isNullOrEmpty()) classroom.number = request.number!!
        if (!request.tags.isNullOrEmpty()) classroom.tags = tagService.getAllTagsByIds(request.tags!!)

        classroomRepository.save(classroom)
        return toClassroomShortInfo(classroom)

    }

    fun deleteClassroom(classroomId: Int) {
        if (!classroomRepository.existsById(classroomId))
            throw NotFoundException(ErrorCode.CLASSROOM_NOT_EXISTS, classroomId.toString())

        classroomRepository.deleteById(classroomId)
    }

    fun getClassroomInfo(classroomId: Int): ClassroomInfo {
        return toClassroomInfo(getClassroomById(classroomId))
    }

    private fun createMetaInfo(building: Building, page: Int, size: Int): MetaInfo {
        val baseUrl = "api/buildings/"
        val total = classroomRepository.countClassroomssByBuilding(building).toInt()
        val lastPageNumber = ceil((total.toDouble() / size.toDouble()) - 1).toInt()
        
        var nextPage: String? = null
        var prevPage: String? = null
        val firstPage = "${baseUrl}${building.id}/classrooms?page=0&size=${size}"
        val lastPage = "${baseUrl}${building.id}?page=${lastPageNumber}&size=${size}"


        if (lastPageNumber > page) {
            nextPage = "${baseUrl}${building.id}/classrooms?page=${page + 1}&size=${size}"
        }
        if (page != 0) {
            prevPage = "${baseUrl}${building.id}/classrooms?page=${page - 1}&size=${size}"
        }

        return MetaInfo(
                total,
                page,
                size,
                nextPage,
                prevPage,
                firstPage,
                lastPage)
    }

    private fun createClassroomInfoByDate(dateFrom: LocalDate,
                                          dateTo: LocalDate,
                                          classroom: Classroom,
                                          eventPeriods: List<EventPeriod>): ClassroomInfoByDate {
        val events: MutableMap<Int, EventInfo> = mutableMapOf()

        eventPeriods.asSequence().forEach { eventPeriod ->
            if (!events.containsKey(eventPeriod.event.id)) {
                events[eventPeriod.event.id] = toEventShortInfo(eventPeriod.event)
            }
            events[eventPeriod.event.id]!!.eventPeriods.add(toEventPeriodShortInfo(eventPeriod))
        }

        return ClassroomInfoByDate(
                dateFrom,
                dateTo,
                toClassroomInfo(classroom),
                events.values)
    }
}
