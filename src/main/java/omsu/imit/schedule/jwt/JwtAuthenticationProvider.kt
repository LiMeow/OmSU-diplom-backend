package omsu.imit.schedule.jwt

import omsu.imit.schedule.exception.JwtAuthenticationException
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

class JwtAuthenticationProvider(private val tokenService: JwtTokenService) : AuthenticationProvider {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val token = authentication.credentials.toString()
        logger.debug("Authenticating {}", token)
        try {
            return tokenService.parseToken(token)
        } catch (e: Exception) {
            throw JwtAuthenticationException("Invalid token received", e)
        }

    }


    override fun supports(authentication: Class<*>): Boolean {
        return JwtToken::class.java.isAssignableFrom(authentication)
    }
}
