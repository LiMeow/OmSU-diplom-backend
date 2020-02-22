package omsu.imit.schedule.jwt

import omsu.imit.schedule.dto.request.SignUpRequest
import omsu.imit.schedule.model.User
import org.springframework.security.core.Authentication
import java.time.Duration

interface JwtTokenService {

    fun parseToken(token: String): Authentication

    fun createToken(user: User): String

    fun getTokenExpiredIn(): Duration

    fun getLongTokenExpiredIn(): Duration

    fun getDataFromToken(token: String): String

    fun createToken(request: SignUpRequest): String

}