package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateClassroomRequest
import omsu.imit.schedule.dto.request.EditClassroomRequest
import omsu.imit.schedule.dto.response.ClassroomShortInfo
import omsu.imit.schedule.dto.response.ClassroomsByBuildingInfo
import omsu.imit.schedule.dto.response.MetaInfo
import omsu.imit.schedule.exception.CommonValidationException
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.model.Classroom
import omsu.imit.schedule.repository.ClassroomRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class ClassroomService
@Autowired
constructor(private val buildingService: BuildingService,
            private val classroomRepository: ClassroomRepository,
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

    fun getClassroomsByTags(tags: List<String>): List<ClassroomShortInfo> {
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

    fun editClassroom(classroomId: Int, request: EditClassroomRequest): ClassroomShortInfo {
        val classroom = getClassroomById(classroomId)

        if (!request.number.isNullOrEmpty()) classroom.number = request.number!!
        if (!request.tags.isNullOrEmpty()) classroom.tags = tagService.getAllTagsByIds(request.tags!!)

        classroomRepository.save(classroom)
        return toClassroomShortInfo(classroom)

    }

    fun deleteClassroom(classroomId: Int) {
        classroomRepository.deleteById(classroomId)
    }

    fun getClassroomInfo(classroomId: Int): ClassroomShortInfo {
        return toClassroomShortInfo(getClassroomById(classroomId))
    }

    private fun createMetaInfo(building: Building, page: Int, size: Int): MetaInfo {
        val baseUrl = "api/buildings/"
        val total = classroomRepository.countClassroomssByBuilding(building).toInt()

        var next: String? = null
        var prev: String? = null
        val first = baseUrl + "${building.id}/classrooms?page=0&size=$size"
        val last = baseUrl + building.id + "?page=" + (total / size).toFloat().roundToInt() + "&size=" + size


        if (Math.round((total / size).toFloat()) > page) {
            next = baseUrl + building.id + "/classrooms?page=" + (page + 1) + "&size=" + size
        }
        if (page != 0) {
            prev = baseUrl + building.id + "/classrooms?page=" + (page - 1) + "&size=" + size
        }

        return MetaInfo(
                total,
                page,
                size,
                next,
                prev,
                first,
                last)
    }
}
