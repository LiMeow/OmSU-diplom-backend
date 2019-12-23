package omsu.imit.schedule.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class JwtSettings(@param:Value("\${jwt.issuer}") val tokenIssuer: String,
                  @param:Value("\${jwt.signingKey}") val tokenSigningKey: String,
                  @param:Value("\${jwt.aTokenDuration}") val aTokenDuration: String,
                  @param:Value("\${jwt.aLongTokenDuration}") val aLongTokenDuration: String) {

    fun getTokenExpiredIn(): Duration {
        return Duration.ofMinutes(aTokenDuration.toLong())
    }

    fun getLongTokenExpiredIn(): Duration {
        return Duration.ofMinutes(aLongTokenDuration.toLong())
    }
}
