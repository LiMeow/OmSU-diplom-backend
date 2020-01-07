package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateAuditoryRequest
import omsu.imit.schedule.dto.request.EditAuditoryRequest
import omsu.imit.schedule.dto.response.AuditoryInfo
import omsu.imit.schedule.dto.response.MetaInfo
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Auditory
import omsu.imit.schedule.repository.AuditoryOccupationRepository
import omsu.imit.schedule.repository.AuditoryRepository
import omsu.imit.schedule.repository.BuildingRepository
import omsu.imit.schedule.repository.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class AuditoryService
@Autowired
constructor(private val auditoryRepository: AuditoryRepository,
            private val auditoryOccupationRepository: AuditoryOccupationRepository,
            private val buildingRepository: BuildingRepository,
            private val tagRepository: TagRepository) : BaseService() {

    fun addAuditory(request: CreateAuditoryRequest): AuditoryInfo {
        val building = buildingRepository.findById(request.buildingId)
                .orElseThrow { NotFoundException(ErrorCode.BUILDING_NOT_EXISTS, request.buildingId.toString()) }

        if (auditoryRepository.findByBuildingAndNumber(request.buildingId, request.number) != null)
            throw NotFoundException(ErrorCode.AUDITORY_ALREADY_EXISTS, request.number, request.buildingId.toString())

        val auditory = Auditory(building, request.number)
        if (!request.tags.isNullOrEmpty()) auditory.tags = tagRepository.findAllById(request.tags!!)

        auditoryRepository.save(auditory)
        return toAuditoryInfo(auditory)
    }

    fun getAuditoryById(auditoryId: Int): AuditoryInfo {
        val auditory = auditoryRepository
                .findById(auditoryId)
                .orElseThrow { NotFoundException(ErrorCode.AUDITORY_NOT_EXISTS, auditoryId.toString()) }
        return toAuditoryInfo(auditory)
    }

    fun getAllAuditoriesByBuilding(buildingId: Int, page: Int, size: Int): List<Auditory>? {
        val pageable: Pageable = PageRequest.of(page, size, Sort.by("number"))
        return auditoryRepository.findAllByBuilding(buildingId, pageable)
    }

    fun editAuditory(auditoryId: Int, request: EditAuditoryRequest): AuditoryInfo {
        val auditory = auditoryRepository
                .findById(auditoryId)
                .orElseThrow { NotFoundException(ErrorCode.AUDITORY_NOT_EXISTS, auditoryId.toString()) }

        if (!request.number.isNullOrEmpty()) auditory.number = request.number!!
        if (!request.tags.isNullOrEmpty()) auditory.tags = tagRepository.findAllById(request.tags!!)

        auditoryRepository.save(auditory)
        return toAuditoryInfo(auditory)

    }

    fun deleteAuditory(auditoryId: Int) {
        auditoryRepository.deleteById(auditoryId)
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
