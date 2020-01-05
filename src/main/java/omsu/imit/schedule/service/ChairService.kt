package omsu.imit.schedule.service

import omsu.imit.schedule.repository.ChairRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class ChairService
@Autowired
constructor(private val chairRepository: ChairRepository) {

    fun getChair(chairId: Int): Any {
        return chairRepository.findById(chairId)
                .orElseThrow { EntityNotFoundException(String.format("Chair with id=%d not found", chairId)) }
    }

    fun deleteChair(chairId: Int) {
        chairRepository.deleteById(chairId)
    }

}