package omsu.imit.schedule.repository

import omsu.imit.schedule.model.ActivityType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ActivityTypeRepository : JpaRepository<ActivityType, Int> {

    @Query("SELECT a FROM ActivityType a WHERE a.type = :type ")
    fun findByActivityType(type: String): ActivityType?
}