package omsu.imit.schedule.repository

import omsu.imit.schedule.model.ConfirmationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ConfirmationTokenRepository : JpaRepository<ConfirmationToken, Int> {

    fun findByToken(token: String): Optional<ConfirmationToken>
}