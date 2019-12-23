package omsu.imit.schedule.service

import com.google.gson.Gson
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import omsu.imit.schedule.jwt.AuthenticatedJwtToken
import omsu.imit.schedule.jwt.JwtSettings
import omsu.imit.schedule.jwt.JwtTokenService
import omsu.imit.schedule.model.User
import omsu.imit.schedule.requests.SignUpRequest
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*


@Service
class JsonWebTokenService(private val settings: JwtSettings) : JwtTokenService {
    private val logger = LoggerFactory.getLogger(JsonWebTokenService::class.java)
    private val gson = Gson()
    private val AUTHORITY = "authority"


    override fun getTokenExpiredIn(): Duration {
        return settings.getTokenExpiredIn()
    }

    override fun getLongTokenExpiredIn(): Duration {
        return settings.getLongTokenExpiredIn()
    }


    override fun createToken(user: User): String {
        logger.debug("Generating token for {}", user.email)
        val now = Instant.now()

        val claims = Jwts.claims()
                .setIssuer(settings.tokenIssuer)
                .setIssuedAt(Date.from(now))
                .setSubject(user.email)
                .setExpiration(Date.from(now.plus(settings.getTokenExpiredIn())))
        claims[AUTHORITY] = user.userRole

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, settings.tokenSigningKey)
                .compact()
    }

    override fun createToken(request: SignUpRequest): String {
        logger.debug("Generating token for request {}", request.email)
        val now = Instant.now()

        val claims = Jwts.claims()
                .setIssuer(settings.tokenIssuer)
                .setIssuedAt(Date.from(now))
                .setSubject(gson.toJson(request))
                .setExpiration(Date.from(now.plus(settings.getLongTokenExpiredIn())))

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, settings.tokenSigningKey)
                .compact()
    }


    override fun parseToken(token: String): Authentication {
        val claims = Jwts.parser()
                .setSigningKey(settings.tokenSigningKey)
                .parseClaimsJws(token)

        val subject = claims.body.subject
        val tokenAuthority = claims.body.get(AUTHORITY, String::class.java)

        val authorities = Collections.singletonList(SimpleGrantedAuthority(tokenAuthority))

        return AuthenticatedJwtToken(subject, authorities)
    }

    override fun getDataFromToken(token: String): String {
        val now = Date.from(Instant.now())

        val claims = Jwts.parser()
                .setSigningKey(settings.tokenSigningKey)
                .parseClaimsJws(token)

        return (if (now > claims.body.expiration) {
            null
        } else claims.body.subject)!!

    }
}
