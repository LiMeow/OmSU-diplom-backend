package omsu.imit.schedule.controller

import omsu.imit.schedule.jwt.JwtTokenService
import omsu.imit.schedule.requests.SignInRequest
import omsu.imit.schedule.requests.SignUpRequest
import omsu.imit.schedule.service.AuthService
import omsu.imit.schedule.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@RestController
class AuthController @Autowired
constructor(private val authService: AuthService,
            private val userService: UserService,
            private val jwtTokenService: JwtTokenService) {

    @PostMapping(
            path = ["/signup"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun signUp(@Valid @RequestBody request: SignUpRequest,
               response: HttpServletResponse): ResponseEntity<*> {

        val userInfo = authService.signUp(request)
        val token = jwtTokenService.createToken(userInfo)
        val cookie = Cookie("accessToken", token)

        cookie.isHttpOnly = true
        cookie.maxAge = (jwtTokenService.getTokenExpiredIn().toMillis() / 1000).toInt()
        response.addCookie(cookie)

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping(
            path = ["/signup/students"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun signUpStudent(@Valid @RequestBody request: SignUpRequest,
                      response: HttpServletResponse): ResponseEntity<*> {

        val userInfo = authService.signUp(request)
        val token = jwtTokenService.createToken(userInfo)
        val cookie = Cookie("accessToken", token)

        cookie.isHttpOnly = true
        cookie.maxAge = (jwtTokenService.getTokenExpiredIn().toMillis() / 1000).toInt()
        response.addCookie(cookie)

        return ResponseEntity.ok(userInfo);
    }

    @PostMapping(
            path = ["/signin"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun signIn(@RequestBody
               request: SignInRequest,
               response: HttpServletResponse): ResponseEntity<*> {

        val userInfo = authService.signIn(request)
        val token = jwtTokenService.createToken(userInfo)
        val cookie = Cookie("accessToken", token)
        cookie.isHttpOnly = true
        cookie.maxAge = (jwtTokenService.getTokenExpiredIn().toMillis() / 1000).toInt()
        response.addCookie(cookie)

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping(
            path = ["/whoiam"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun whoIAm(): ResponseEntity<*> {

        return ResponseEntity.ok(userService.whoIAm());
    }

    @DeleteMapping(
            path = ["/signout"],
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun signOut(response: HttpServletResponse): ResponseEntity<*> {
        val cookie = Cookie("accessToken", "")
        cookie.isHttpOnly = true
        cookie.maxAge = (0)
        response.addCookie(cookie)
        return ResponseEntity.noContent().build<Any>()
    }
}
