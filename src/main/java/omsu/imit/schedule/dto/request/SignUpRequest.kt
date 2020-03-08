package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.UserRole
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class SignUpRequest(@get:NotBlank var firstName: String,
                         var patronymic: String?,
                         @get:NotBlank var lastName: String,
                         @get:NotBlank @get:Email var email: String,
                         @get:NotBlank var password: String,
                         @get:NotNull var userRole: UserRole)
