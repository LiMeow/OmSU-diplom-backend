package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.Role
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class SignUpRequest(@get:NotBlank var firstName: String,
                         var patronymic: String?,
                         @get:NotBlank var lastName: String,
                         @get:NotBlank @get:Email var email: String,
                         @get:NotBlank var password: String,
                         var role: Role)
