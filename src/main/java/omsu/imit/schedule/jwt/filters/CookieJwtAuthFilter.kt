package omsu.imit.schedule.jwt.filters

import omsu.imit.schedule.exception.JwtAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.util.WebUtils

import javax.servlet.http.HttpServletRequest

class CookieJwtAuthFilter(matcher: RequestMatcher) : JwtAuthFilter(matcher) {

    @Throws(AuthenticationException::class)
    override fun takeToken(request: HttpServletRequest): String {
        val cookie = WebUtils.getCookie(request, "accessToken")
        return if (cookie != null) {
            cookie.value
        } else {
            throw JwtAuthenticationException("Invalid 'accessToken' cookie: " + cookie!!)
        }
    }
}
