package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Auditory
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AuditoryRepository : JpaRepository<Auditory, Int> {

    @Query(value = "SELECT a from Auditory a WHERE a.building.id = :buildingId AND a.number = :number")
    fun findByBuildingAndNumber(@Param("buildingId") buildingId: Int,
                                @Param("number") number: String): Auditory?

    @Query(value = "SELECT a from Auditory a WHERE a.building.id = :buildingId")
    fun findAllByBuilding(@Param("buildingId") buildingId: Int,
                          pageable: Pageable): List<Auditory>?


}