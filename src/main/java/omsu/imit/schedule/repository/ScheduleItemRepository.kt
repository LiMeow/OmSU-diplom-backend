package omsu.imit.schedule.repository

import omsu.imit.schedule.model.ScheduleItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ScheduleItemRepository : JpaRepository<ScheduleItem, Int> {
    @Query("SELECT s FROM ScheduleItem s WHERE s.auditoryOccupation.lecturer.id = :lecturerId")
    fun findByLecturer(@Param("lecturerId") lecturerId: Int): List<ScheduleItem>
}