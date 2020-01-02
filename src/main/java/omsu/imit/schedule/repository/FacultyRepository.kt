package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Faculty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface FacultyRepository : JpaRepository<Faculty, Int> {
    @Query("SELECT f FROM Faculty f WHERE f.name = :name AND f.building.id = :buildingId")
    fun findByNameAndAddress(@Param("name") name: String,
                             @Param("buildingId") buildingId: Int): Faculty?
}