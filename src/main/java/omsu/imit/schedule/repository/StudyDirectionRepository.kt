package omsu.imit.schedule.repository

import omsu.imit.schedule.model.StudyDirection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudyDirectionRepository : JpaRepository<StudyDirection, Int>