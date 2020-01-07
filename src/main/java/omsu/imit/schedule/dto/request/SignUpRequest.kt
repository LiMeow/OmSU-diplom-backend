package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.UserRole
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class SignUpRequest(
        var firstName: String,
        var patronymic: String?,
        var lastName: String,
        @NotBlank @Email
        var email: String,
        @NotBlank
        var password: String,
        @NotNull
        var userRole: UserRole)
