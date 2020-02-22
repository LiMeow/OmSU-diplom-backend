package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateAuditoryRequest
import omsu.imit.schedule.dto.request.EditAuditoryRequest
import omsu.imit.schedule.dto.response.AuditoriesByBuildingInfo
import omsu.imit.schedule.dto.response.AuditoryShortInfo
import omsu.imit.schedule.dto.response.MetaInfo
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Auditory
import omsu.imit.schedule.model.Building
import omsu.imit.schedule.repository.AuditoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

@Service
class AuditoryService
@Autowired
constructor(private val auditoryRepository: AuditoryRepository,
            private val buildingService: BuildingService,
            private val tagService: TagService) : BaseService() {

    fun addAuditory(request: CreateAuditoryRequest): AuditoryShortInfo {
        val building = buildingService.getBuildingById(request.buildingId);

        if (auditoryRepository.findByBuildingAndNumber(request.buildingId, request.number) != null)
            throw NotFoundException(ErrorCode.AUDITORY_ALREADY_EXISTS, request.number, request.buildingId.toString())

        val auditory = Auditory(building, request.number)

        if (!request.tags.isNullOrEmpty()) auditory.tags = tagService.getAllTagsByIds(request.tags!!)

        auditoryRepository.save(auditory)
        return toAuditoryShortInfo(auditory)
    }

    fun getAuditoryById(auditoryId: Int): Auditory {
        return auditoryRepository
                .findById(auditoryId)
                .orElseThrow { NotFoundException(ErrorCode.AUDITORY_NOT_EXISTS, auditoryId.toString()) }
    }

    fun getAuditoriesByTags(tags: List<String>): Any {
        return auditoryRepository.findAllByTags(tags).asSequence().map { toAuditoryShortInfo(it) }.toList();
    }

    fun getAllAuditoriesByBuilding(buildingId: Int): List<Auditory>? {
        return auditoryRepository.findAllByBuilding(buildingId, Sort.by("number"))
    }

    fun getAllAuditoriesByBuilding(buildingId: Int, page: Int, size: Int): AuditoriesByBuildingInfo {
        val building: Building = buildingService.getBuildingById(buildingId);
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("number"))

        val auditories = auditoryRepository
                .findAllByBuilding(buildingId, pageable)
                .asSequence()
                .map { toAuditoryShortInfo(it) }
                .toList()
        return AuditoriesByBuildingInfo(createMetaInfo(building, page, size), auditories)
    }

    fun editAuditory(auditoryId: Int, request: EditAuditoryRequest): AuditoryShortInfo {
        val auditory = getAuditoryById(auditoryId)

        if (!request.number.isNullOrEmpty()) auditory.number = request.number!!
        if (!request.tags.isNullOrEmpty()) auditory.tags = tagService.getAllTagsByIds(request.tags!!)

        auditoryRepository.save(auditory)
        return toAuditoryShortInfo(auditory)

    }

    fun deleteAuditory(auditoryId: Int) {
        auditoryRepository.deleteById(auditoryId)
    }

    fun getAuditoryInfo(auditoryId: Int): AuditoryShortInfo {
        return toAuditoryShortInfo(getAuditoryById(auditoryId))
    }

    private fun createMetaInfo(building: Building, page: Int, size: Int): MetaInfo {
        val total = auditoryRepository.countAuditoriesByBuilding(building).toInt()

        var next: String? = null
        var prev: String? = null
        val first = "/auditories/building/${building.id}?page=0&size=$size"
        val last = "/auditories/building/" + building.id + "?page=" + (total / size).toFloat().roundToInt() + "&size=" + size


        if (Math.round((total / size).toFloat()) > page) {
            next = "/auditories/building/" + building.id + "?page=" + (page + 1) + "&size=" + size
        }
        if (page != 0) {
            prev = "/auditories/building/" + building.id + "?page=" + (page - 1) + "&size=" + size
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
