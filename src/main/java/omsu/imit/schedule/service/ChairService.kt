package omsu.imit.schedule.service

import omsu.imit.schedule.exception.ErrorCode
import omsu.imit.schedule.exception.NotFoundException
import omsu.imit.schedule.model.Chair
import omsu.imit.schedule.repository.ChairRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChairService
@Autowired
constructor(private val chairRepository: ChairRepository) {

    fun getChair(chairId: Int): Chair {
        return chairRepository.findById(chairId)
                .orElseThrow { NotFoundException(ErrorCode.CHAIR_NOT_EXISTS, chairId.toString()) }
    }

    fun deleteChair(chairId: Int) {
        if (!chairRepository.existsById(chairId))
            throw  NotFoundException(ErrorCode.CHAIR_NOT_EXISTS, chairId.toString())
        chairRepository.deleteById(chairId)
    }

}