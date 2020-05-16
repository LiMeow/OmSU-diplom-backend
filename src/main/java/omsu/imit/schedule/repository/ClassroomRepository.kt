package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Building
import omsu.imit.schedule.model.Classroom
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ClassroomRepository : JpaRepository<Classroom, Int> {

    @Query(value = "SELECT a from Classroom a WHERE a.building.id = :buildingId AND a.number = :number")
    fun findByBuildingAndNumber(@Param("buildingId") buildingId: Int,
                                @Param("number") number: String): Classroom?

    @Query(value = "SELECT a from Classroom a WHERE a.building.id = :buildingId")
    fun findAllByBuilding(@Param("buildingId") buildingId: Int,
                          sort: Sort): List<Classroom>?

    @Query(value = "SELECT a from Classroom a WHERE a.building.id = :buildingId")
    fun findAllByBuilding(@Param("buildingId") buildingId: Int,
                          pageable: Pageable): List<Classroom>

    fun countClassroomssByBuilding(building: Building): Long

    @Query(value = "select *" +
            "from classroom " +
            "where classroom.id IN ( " +
            "    select classroom_id" +
            "    from classroom_tag" +
            "    where tag_id IN :tags);", nativeQuery = true)
    fun findAllByTags(@Param("tags") tags: List<Int>): List<Classroom>

}