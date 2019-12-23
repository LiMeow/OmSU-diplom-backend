package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Int> {

    @Query("SELECT t FROM Tag t WHERE t.tag = :tag")
    fun findByTag(@Param("tag") tag: String): Tag?
}
