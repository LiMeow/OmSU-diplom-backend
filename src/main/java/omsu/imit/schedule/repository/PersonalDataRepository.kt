package omsu.imit.schedule.repository

import omsu.imit.schedule.model.PersonalData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonalDataRepository : JpaRepository<PersonalData, Int>