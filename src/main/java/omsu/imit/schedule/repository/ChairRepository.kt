package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Chair
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChairRepository : JpaRepository<Chair, Int> {
}