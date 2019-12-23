package omsu.imit.schedule.jwt.filters

import omsu.imit.schedule.jwt.JwtToken
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class JwtAuthFilter(matcher: RequestMatcher) : AbstractAuthenticationProcessingFilter(matcher) {
    private val LOGGER = LoggerFactory.getLogger(JwtAuthFilter::class.java)

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val token: String
        try {
            token = takeToken(request)
        } catch (e: Exception) {
            LOGGER.warn("Failed to get token: {}", e.message)
            return anonymousToken()
        }

        return JwtToken(token)
    }

    @Throws(AuthenticationException::class)
    protected abstract fun takeToken(request: HttpServletRequest): String

    private fun anonymousToken(): Authentication {
        return AnonymousAuthenticationToken("ANONYMOUS", "ANONYMOUS", listOf(SimpleGrantedAuthority("ROLE_ANONYMOUS")))
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain?, authResult: Authentication) {
        SecurityContextHolder.getContext().authentication = authResult
        chain!!.doFilter(request, response)
    }
}
