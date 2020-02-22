package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Schedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ScheduleRepository : JpaRepository<Schedule, Int> {

    @Query("SELECT s FROM Schedule s WHERE s.studyYear = :studyYear AND  s.semester = :semester")
    fun findByStudyYearAndSemester(@Param("studyYear") studyYear: String,
                                   @Param("semester") semester: Int): List<Schedule>

    @Query("SELECT s FROM Schedule s WHERE s.group.id = :groupId")
    fun findByGroup(groupId: Int): List<Schedule>
}