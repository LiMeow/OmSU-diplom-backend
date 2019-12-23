package omsu.imit.schedule.repository

import omsu.imit.schedule.model.TimeBlock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface TimeBlockRepository : JpaRepository<TimeBlock, Int> {

    @Query("SELECT t FROM TimeBlock t WHERE t.timeFrom = :timeFrom AND t.timeTo = :timeTo")
    fun findByTime(@Param("timeFrom") timeFrom: String,
                   @Param("timeTo") timeTo: String): TimeBlock?
}
