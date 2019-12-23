package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.ScheduleGeneratorException
import omsu.imit.schedule.model.Auditory
import omsu.imit.schedule.repository.AuditoryOccupationRepository
import omsu.imit.schedule.repository.AuditoryRepository
import omsu.imit.schedule.repository.BuildingRepository
import omsu.imit.schedule.repository.TagRepository
import omsu.imit.schedule.requests.CreateAuditoryRequest
import omsu.imit.schedule.requests.EditAuditoryRequest
import omsu.imit.schedule.response.AuditoryFullInfo
import omsu.imit.schedule.response.AuditoryInfo
import omsu.imit.schedule.response.MetaInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuditoryService
@Autowired
constructor(private val auditoryRepository: AuditoryRepository,
            private val auditoryOccupationRepository: AuditoryOccupationRepository,
            private val buildingRepository: BuildingRepository,
            private val tagRepository: TagRepository) : BaseService() {

    fun addAuditory(request: CreateAuditoryRequest): AuditoryInfo {
        val building = buildingRepository.findById(request.buildingId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.BUILDING_NOT_EXISTS, request.buildingId.toString())

        if (auditoryRepository.findByBuildingAndNumber(request.buildingId, request.number) != null)
            throw ScheduleGeneratorException(ErrorCode.AUDITORY_ALREADY_EXISTS, request.number, request.buildingId.toString())

        val auditory = Auditory(building, request.number)
        if (!request.tags.isNullOrEmpty()) auditory.tags = tagRepository.findAllById(request.tags!!)

        auditoryRepository.save(auditory)
        return createAuditoryInfo(auditory)
    }

    fun getAuditoryById(auditoryId: Int): AuditoryInfo {
        val auditory = auditoryRepository.findById(auditoryId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.AUDITORY_NOT_EXISTS, auditoryId.toString())
        return createAuditoryInfo(auditory)
    }

    fun getAllAuditoriesByDate(buildingId: Int, date: String, page: Int, size: Int): AuditoryFullInfo {
        if (!buildingRepository.existsById(buildingId))
            throw ScheduleGeneratorException(ErrorCode.BUILDING_NOT_EXISTS, buildingId.toString())

        val pageable: Pageable = PageRequest.of(page, size, Sort.by("number"))
        val auditories = auditoryRepository.findAllByBuilding(buildingId, pageable)
        val auditoryInfoList = ArrayList<AuditoryInfo>()

        for (auditory in auditories!!) {
            auditory.auditoryOccupations = auditoryOccupationRepository.findByAuditoryAndDate(auditory.id, date)
            auditoryInfoList.add(createAuditoryInfo(auditory))
        }
        return AuditoryFullInfo(createMetaInfo(buildingId, page, size), auditoryInfoList)
    }

    fun editAuditory(auditoryId: Int, request: EditAuditoryRequest): AuditoryInfo {
        val auditory = auditoryRepository.findById(auditoryId).orElse(null)
                ?: throw ScheduleGeneratorException(ErrorCode.AUDITORY_NOT_EXISTS, auditoryId.toString());

        if (!request.number.isNullOrEmpty()) auditory.number = request.number!!
        if (!request.tags.isNullOrEmpty()) auditory.tags = tagRepository.findAllById(request.tags!!)
        if (request.number.isNullOrEmpty() && request.tags.isNullOrEmpty()) throw ScheduleGeneratorException(ErrorCode.EMPTY_REQUEST_BODY)

        auditoryRepository.save(auditory)
        return createAuditoryInfo(auditory)

    }

    fun deleteAuditory(auditoryId: Int) {
        if (!auditoryRepository.existsById(auditoryId))
            throw ScheduleGeneratorException(ErrorCode.AUDITORY_NOT_EXISTS, auditoryId.toString())

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
