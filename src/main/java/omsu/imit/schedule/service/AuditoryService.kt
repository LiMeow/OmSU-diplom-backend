package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateAuditoryRequest
import omsu.imit.schedule.dto.request.EditAuditoryRequest
import omsu.imit.schedule.dto.response.AuditoryShortInfo
import omsu.imit.schedule.dto.response.MetaInfo
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Auditory
import omsu.imit.schedule.repository.AuditoryOccupationRepository
import omsu.imit.schedule.repository.AuditoryRepository
import omsu.imit.schedule.repository.BuildingRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class AuditoryService
@Autowired
constructor(private val auditoryRepository: AuditoryRepository,
            private val buildingService: BuildingService,
            private val auditoryOccupationRepository: AuditoryOccupationRepository,
            private val buildingRepository: BuildingRepository,
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

    fun getAllAuditoriesByBuilding(buildingId: Int): List<Auditory>? {
        return auditoryRepository.findAllByBuilding(buildingId, Sort.by("number"))
    }

    fun getAllAuditoriesByBuilding(buildingId: Int, page: Int, size: Int): List<AuditoryShortInfo> {
        var newSize = Int.MAX_VALUE
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("number"))
        return auditoryRepository.findAllByBuilding(buildingId, pageable).asSequence().map { toAuditoryShortInfo(it) }.toList()
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

    private fun createMetaInfo(buildingId: Int, page: Int, size: Int): MetaInfo {
        val total = auditoryRepository!!.count().toInt()
        var next: String? = null
        var prev: String? = null


        if (Math.round((total / size).toFloat()) > page) {
            next = "/auditories/building/" + buildingId + "?page=" + (page + 1) + "&size=" + size
        }
        if (page != 0) {
            prev = "/auditories/building/" + buildingId + "?page=" + (page - 1) + "&size=" + size
        }

        return MetaInfo(
                total,
                page,
                size,
                next,
                prev,
                "/auditories/building/$buildingId?page=0&size=$size",
                "/auditories/building/" + buildingId + "?page=" + Math.round((total / size).toFloat()) + "&size=" + size)
    }
}
