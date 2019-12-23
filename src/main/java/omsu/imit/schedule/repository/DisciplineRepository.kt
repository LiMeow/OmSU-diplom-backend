package omsu.imit.schedule.repository

import omsu.imit.schedule.model.Discipline
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface DisciplineRepository : JpaRepository<Discipline, Int> {

    @Query("SELECT d FROM Discipline d WHERE d.name = :name ")
    fun findByDisciplineName(name: String): Discipline?
}