package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ScheduleRepository : JpaRepository<Schedule, Int> {
    
    @Query("SELECT s FROM Schedule s WHERE s.group.id = :groupId AND s.studyYear = :studyYear AND  s.semester = :semester")
    fun findByGroup(groupId: Int, studyYear: String, semester: Int): List<Schedule>
}