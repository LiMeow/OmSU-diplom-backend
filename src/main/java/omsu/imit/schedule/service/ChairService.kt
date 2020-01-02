package omsu.imit.schedule.service

import omsu.imit.schedule.repository.ChairRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ChairService
@Autowired
constructor(private val chairRepository: ChairRepository) {

    fun createChair(request: Int, chairName: String): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getChair(chairId: Int): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteChair(chairId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}