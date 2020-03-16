package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotBlank

data class SignInRequest(@get:NotBlank var email: String,
                         @get:NotBlank var password: String)

