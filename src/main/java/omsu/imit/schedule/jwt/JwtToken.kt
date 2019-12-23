package omsu.imit.schedule.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken

class JwtToken(private val token: String) : AbstractAuthenticationToken(null) {

    init {
        super.setAuthenticated(false)
    }

    override fun setAuthenticated(authenticated: Boolean) {
        if (authenticated) {
            throw IllegalArgumentException("Cannot set this token to trusted")
        }
        super.setAuthenticated(false)
    }

    override fun getCredentials(): Any {
        return token
    }

    override fun getPrincipal(): Any? {
        return null
    }
}
