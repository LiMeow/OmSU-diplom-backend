package omsu.imit.schedule.service

import omsu.imit.schedule.dto.request.CreateChairRequest
import omsu.imit.schedule.dto.response.ChairInfo
import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Chair
import omsu.imit.schedule.repository.ChairRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChairService
@Autowired
constructor(private val chairRepository: ChairRepository,
            private val facultyService: FacultyService) : BaseService() {

    fun createChair(request: CreateChairRequest): ChairInfo {
        val faculty = facultyService.getFacultyById(request.facultyId)
        val chair = Chair(faculty, request.name)

        chairRepository.save(chair)
        return toChairInfo(chair)
    }

    fun getChairById(chairId: Int): Chair {
        return chairRepository.findById(chairId)
                .orElseThrow { NotFoundException(ErrorCode.CHAIR_NOT_EXISTS, chairId.toString()) }
    }

    fun getAllChairs(): List<ChairInfo> {
        return chairRepository.findAll().asSequence().map { toChairInfo(it) }.toList()
    }

    fun deleteChair(chairId: Int) {
        if (!chairRepository.existsById(chairId))
            throw  NotFoundException(ErrorCode.CHAIR_NOT_EXISTS, chairId.toString())
        chairRepository.deleteById(chairId)
    }

    fun getChairInfo(chairId: Int): ChairInfo {
        return toChairInfo(getChairById(chairId))
    }
}