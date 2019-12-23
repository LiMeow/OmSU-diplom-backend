package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Building
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BuildingRepository : JpaRepository<Building, Int> {
    @Query("SELECT b FROM Building b WHERE b.number = :number AND b.address = :address")
    fun findByNumberAndAddress(@Param("number") number: Int,
                               @Param("address") address: String): Building?
}