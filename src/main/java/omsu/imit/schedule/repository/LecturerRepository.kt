package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Lecturer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LecturerRepository : JpaRepository<Lecturer, Int> {

    fun findAllByChairId(chair_id: Int): List<Lecturer>
}