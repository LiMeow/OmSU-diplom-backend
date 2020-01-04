package omsu.imit.schedule.repository

import omsu.imit.schedule.model.PersonalData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PersonalDataRepository : JpaRepository<PersonalData, Int> {

    @Query("SELECT u FROM PersonalData u WHERE u.email = :email ")
    fun findByEmail(@Param("email") email: String): PersonalData?


}