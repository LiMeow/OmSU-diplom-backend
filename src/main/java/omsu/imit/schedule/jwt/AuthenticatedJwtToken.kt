package omsu.imit.schedule.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class AuthenticatedJwtToken(private val subject: String,
                            authority: Collection<GrantedAuthority>) : AbstractAuthenticationToken(authority) {

    init {
        isAuthenticated = true
    }


    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any {
        return subject
    }
}
