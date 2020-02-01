package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.SignInRequest
import omsu.imit.schedule.dto.request.SignUpRequest
import omsu.imit.schedule.jwt.JwtTokenService
import omsu.imit.schedule.service.AuthService
import omsu.imit.schedule.service.UserService
import org.springframework.beans.factory.annotation.Autowired
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

    @PostMapping(path = ["/api/signup"])
    fun signUp(@Valid @RequestBody request: SignUpRequest,
               response: HttpServletResponse): ResponseEntity<*> {

        val userInfo = authService.signUp(request)
        val token = jwtTokenService.createToken(userInfo)
        val cookie = Cookie("accessToken", token)

        cookie.isHttpOnly = true
        cookie.maxAge = (jwtTokenService.getTokenExpiredIn().toMillis() / 1000).toInt()
        response.addCookie(cookie)

        authService.sendVerificationToken(userInfo);
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping(path = ["/api/signin"])
    fun signIn(@Valid @RequestBody request: SignInRequest,
               response: HttpServletResponse): ResponseEntity<*> {

        val userInfo = authService.signIn(request)
        val token = jwtTokenService.createToken(userInfo)
        val cookie = Cookie("accessToken", token)
        cookie.isHttpOnly = true
        cookie.maxAge = (jwtTokenService.getTokenExpiredIn().toMillis() / 1000).toInt()
        response.addCookie(cookie)

        return ResponseEntity.ok(userInfo);
    }

    @DeleteMapping(path = ["/api/signout"])
    fun signOut(response: HttpServletResponse): ResponseEntity<*> {
        val cookie = Cookie("accessToken", "")
        cookie.isHttpOnly = true
        cookie.maxAge = (0)
        response.addCookie(cookie)
        return ResponseEntity.noContent().build<Any>()
    }

    @GetMapping(path = ["/api/whoiam"])
    fun whoIAm(): ResponseEntity<*> {

        return ResponseEntity.ok(userService.whoIAm());
    }

    @GetMapping(path = ["/api/confirm-account"])
    fun confirmAccount(@RequestParam token: String): ResponseEntity<*> {
        return ResponseEntity.ok(authService.confirmAccount(token));
    }

}
