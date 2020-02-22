package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface EventRepository : JpaRepository<Event, Int> {

    /*TODO("make refactoring of method")*/
    @Query("SELECT a FROM Event a " +
            "WHERE a.classroom.id = :classroomId ")
    fun findByClassroomAndDate(@Param("classroomId") classroomId: Int): List<Event>?

    @Query("SELECT a FROM Event a " +
            "WHERE a.classroom.id = :classroomId AND a.day =:day AND a.timeBlock.id =:timeBlockId AND a.dateFrom <= :dateTo AND a.dateTo >= :dateFrom")
    fun findByClassroomDayAndTime(@Param("classroomId") classroomId: Int,
                                  @Param("day") day: Day,
                                  @Param("dateFrom") dateFrom: Date,
                                  @Param("dateTo") dateTo: Date,
                                  @Param("timeBlockId") timeBlockId: Int): List<Event>

    @Modifying
    @Transactional
    @Query("DELETE FROM Event a WHERE a.classroom.id = :classroomId ")
    fun deleteAllByClassroom(@Param("classroomId") classroomId: Int)


}