package omsu.imit.schedule.repository

import omsu.imit.schedule.model.AuditoryOccupation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface AuditoryOccupationRepository : JpaRepository<AuditoryOccupation, Int> {

    /*TODO("make refactoring of method")*/
    @Query("SELECT a FROM AuditoryOccupation a " +
            "WHERE a.auditory.id = :auditoryId ")
    fun findByAuditoryAndDate(@Param("auditoryId") auditoryId: Int): List<AuditoryOccupation>?


    @Modifying
    @Transactional
    @Query("DELETE FROM AuditoryOccupation a WHERE a.auditory.id = :auditoryId ")
    fun deleteAllByAuditory(@Param("auditoryId") auditoryId: Int)


}