package omsu.imit.schedule.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import omsu.imit.schedule.exception.JwtAuthenticationException
import omsu.imit.schedule.model.Role
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors
import javax.annotation.PostConstruct

@Component
class JwtTokenProvider {

    @Autowired
    private lateinit var customUserDetails: CustomUserDetails

    @Value("\${security.jwt.token.secret-key:secret-key}")
    private var secretKey: String? = null

    @Value("\${security.jwt.token.expire-length:3600000}")
    private val validityInMilliseconds: Long = 3600000 // 1h

    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey!!.toByteArray())
    }

    fun createToken(username: String?, roles: List<Role>): String {
        val claims = Jwts.claims().setSubject(username)
        claims["auth"] = roles.stream()
                .map { s: Role -> SimpleGrantedAuthority(s.authority) }
                .filter { obj: SimpleGrantedAuthority? -> Objects.nonNull(obj) }
                .collect(Collectors.toList())

        val now = Date()
        val validity = Date(now.time + validityInMilliseconds)
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    fun getAuthentication(token: String?): Authentication {
        val userDetails: UserDetails = customUserDetails.loadUserByUsername(getUsername(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUsername(token: String?): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return true
        } catch (e: JwtException) {
            throw JwtAuthenticationException("Invalid token received", e)
        } catch (e: IllegalArgumentException) {
            throw JwtAuthenticationException("Invalid token received", e)
        }
    }
}