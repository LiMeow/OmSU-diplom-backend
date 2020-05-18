package omsu.imit.schedule.security

import omsu.imit.schedule.exception.JwtAuthenticationException
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.WebUtils
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtTokenFilter(private val jwtTokenProvider: JwtTokenProvider) : OncePerRequestFilter() {

    @Throws(AuthenticationException::class)
    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain) {
        val token = WebUtils.getCookie(request, "accessToken")?.value
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                val auth: Authentication = jwtTokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (ex: JwtAuthenticationException) {
            SecurityContextHolder.clearContext()
            throw JwtAuthenticationException("Invalid 'accessToken': " + token!!)
        }
        filterChain.doFilter(request, response)
    }

}