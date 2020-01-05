package omsu.imit.schedule.repository

import omsu.imit.schedule.model.ScheduleItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ScheduleItemRepository : JpaRepository<ScheduleItem, Int> {
}