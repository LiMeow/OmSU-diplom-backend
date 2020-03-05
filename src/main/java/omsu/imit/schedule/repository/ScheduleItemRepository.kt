package omsu.imit.schedule.repository

import omsu.imit.schedule.model.ScheduleItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ScheduleItemRepository : JpaRepository<ScheduleItem, Int> {
    @Query("SELECT s FROM ScheduleItem s WHERE s.event.lecturer.id = :lecturerId")
    fun findByLecturer(@Param("lecturerId") lecturerId: Int): List<ScheduleItem>

    @Query(value = "SELECT (schedule_item.*) FROM schedule " +
            "JOIN schedule_item ON schedule.id = schedule_item.schedule_id " +
            "JOIN event ON schedule_item.event_id = event.id " +
            "WHERE event.lecturer_id = :lecturerId " +
            "AND study_year = :studyYear " +
            "AND semester%2 = :semester", nativeQuery = true)
    fun findByLecturer(lecturerId: Int, studyYear: String, semester: Int): List<ScheduleItem>
}