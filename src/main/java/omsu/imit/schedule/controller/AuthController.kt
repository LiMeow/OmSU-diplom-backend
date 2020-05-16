package omsu.imit.schedule.controller

import omsu.imit.schedule.dto.request.SignInRequest
import omsu.imit.schedule.dto.request.SignUpRequest
import omsu.imit.schedule.dto.response.StatusResponse
import omsu.imit.schedule.model.Role
import omsu.imit.schedule.security.JwtTokenProvider
import omsu.imit.schedule.service.AuthService
import omsu.imit.schedule.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid


@RestController
class AuthController @Autowired
constructor(private val authService: AuthService,
            private val userService: UserService,
            private val jwtTokenProvider: JwtTokenProvider) {

    private val COOKIE_LIFE_TIME = 3570000; //59.5 min

    @PostMapping(path = ["/signup"])
    fun signUp(@Valid @RequestBody request: SignUpRequest,
               response: HttpServletResponse): ResponseEntity<*> {

        val userInfo = authService.signUp(request)
        val cookie = getCookie(userInfo.email, userInfo.role)
        response.addCookie(cookie)

        authService.sendVerificationToken(userInfo);
        return ResponseEntity.ok(userInfo);
    }

    @PostMapping(path = ["/signin"])
    fun signIn(@Valid @RequestBody request: SignInRequest,
               response: HttpServletResponse): ResponseEntity<*> {

        val userInfo = authService.signIn(request)
        val cookie = getCookie(userInfo.email, userInfo.role)
        response.addCookie(cookie)

        return ResponseEntity.ok(userInfo);
    }

    @DeleteMapping(path = ["/signout"])
    fun signOut(response: HttpServletResponse): ResponseEntity<*> {
        val cookie = Cookie("accessToken", "")

        cookie.isHttpOnly = true
        cookie.maxAge = (0)
        response.addCookie(cookie)

        return ResponseEntity.noContent().build<Any>()
    }

    @GetMapping("/refresh")
    fun refresh(req: HttpServletRequest, response: HttpServletResponse): StatusResponse {
        val cookie = getCookie(req.remoteUser)
        response.addCookie(cookie)

        return StatusResponse.OK;
    }

    @GetMapping(path = ["/whoiam"])
    fun whoIAm(): ResponseEntity<*> {
        val email = SecurityContextHolder.getContext().authentication.name;
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @GetMapping(path = ["/confirm-account"])
    fun confirmAccount(@RequestParam token: String): ResponseEntity<*> {
        return ResponseEntity.ok(authService.confirmAccount(token));
    }

    fun getCookie(email: String, role: Role = Role.ROLE_ADMIN): Cookie {
        val token = jwtTokenProvider.createToken(email, listOf(role));
        val cookie = Cookie("accessToken", token)

        cookie.isHttpOnly = true
        cookie.maxAge = (COOKIE_LIFE_TIME);

        return cookie
    }
}
