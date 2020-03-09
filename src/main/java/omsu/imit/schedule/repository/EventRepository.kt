package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : JpaRepository<Event, Int> {

//    /*TODO("make refactoring of method")*/
//    @Query("SELECT a FROM Event a " +
//            "WHERE a.classroom.id = :classroomId ")
//    fun findByClassroomAndDate(@Param("classroomId") classroomId: Int): List<Event>?
}