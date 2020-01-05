package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ScheduleRepository  : JpaRepository<Schedule, Int> {
}