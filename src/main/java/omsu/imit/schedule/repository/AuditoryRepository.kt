package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Auditory
import omsu.imit.schedule.model.Building
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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
                          sort: Sort): List<Auditory>?

    @Query(value = "SELECT a from Auditory a WHERE a.building.id = :buildingId")
    fun findAllByBuilding(@Param("buildingId") buildingId: Int,
                          pageable: Pageable): List<Auditory>

    fun countAuditoriesByBuilding(building: Building): Long

    @Query(value = "select *" +
            "from auditory " +
            "where auditory.id IN ( " +
            "    select auditory_id" +
            "    from auditory_tag" +
            "    where tag_id IN (" +
            "        select t.id" +
            "        from tag t" +
            "        where t.tag in :tags));", nativeQuery = true)
    fun findAllByTags(@Param("tags") tags: List<String>): List<Auditory>


}