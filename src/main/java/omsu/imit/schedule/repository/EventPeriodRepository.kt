package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.EventPeriod
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EventPeriodRepository : JpaRepository<EventPeriod, Int> {

    @Query("SELECT e FROM EventPeriod e " +
            "WHERE e.classroom.id = :classroomId " +
            "AND e.day =:day " +
            "AND e.timeBlock.id =:timeBlockId " +
            "AND e.dateFrom <= :dateTo " +
            "AND e.dateTo >= :dateFrom")
    fun findByClassroomDayAndTime(@Param("classroomId") classroomId: Int,
                                  @Param("day") day: Day,
                                  @Param("dateFrom") dateFrom: Date,
                                  @Param("dateTo") dateTo: Date,
                                  @Param("timeBlockId") timeBlockId: Int): List<EventPeriod>


    @Query("SELECT e FROM EventPeriod e " +
            "WHERE e.classroom.id = :classroomId " +
            "AND e.day =:day " +
            "AND e.dateFrom <= :date " +
            "AND e.dateTo >= :date"
    )
    fun findAllByClassroomDayAndDate(@Param("classroomId") classroomId: Int,
                                     @Param("day") day: Day,
                                     @Param("date") date: Date): List<EventPeriod>
}