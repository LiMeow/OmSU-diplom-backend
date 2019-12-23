package omsu.imit.schedule.jwt.filters

import omsu.imit.schedule.exception.JwtAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest

class HeaderJwtAuthFilter(matcher: RequestMatcher) : JwtAuthFilter(matcher) {

    @Throws(AuthenticationException::class)
    override fun takeToken(request: HttpServletRequest): String {
        val authHeader = request.getHeader("Authorization")
        val m = BEARER_AUTH_PATTERN.matcher(authHeader)
        return if (m.matches()) {
            m.group(TOKEN_GROUP)
        } else {
            throw JwtAuthenticationException("Invalid Authorization header: $authHeader")
        }
    }

    companion object {
        private val BEARER_AUTH_PATTERN = Pattern.compile("^Bearer\\s+(.*)$")
        private val TOKEN_GROUP = 1
    }
}
