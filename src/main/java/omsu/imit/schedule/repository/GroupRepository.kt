package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository : JpaRepository<Group, Int> {

    @Query("SELECT g FROM Group g WHERE g.name = :name AND g.studyDirection.id = :studyDirectionId")
    fun findByNameAndDirection(@Param("name") name: String,
                               @Param("studyDirectionId") studyDirectionId: Int): Group?
}