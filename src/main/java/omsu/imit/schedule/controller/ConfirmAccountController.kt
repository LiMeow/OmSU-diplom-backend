package omsu.imit.schedule.controller

import omsu.imit.schedule.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class ConfirmAccountController @Autowired
constructor(private val authService: AuthService) {

    @GetMapping("/confirm-account")
    fun confirmAccount(@RequestParam token: String): String {
        authService.confirmAccount(token)
        return "redirect:https://i-scheduler-app.herokuapp.com/login";
    }
}