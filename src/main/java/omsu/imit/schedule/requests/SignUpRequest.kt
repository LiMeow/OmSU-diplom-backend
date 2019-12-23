package omsu.imit.schedule.requests

import omsu.imit.schedule.model.UserRole
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

class SignUpRequest(
        var firstName: String,
        var patronymic: String?,
        var lastName: String,
        @NotNull @Email
        var email: String,
        @NotNull
        var password: String,
        @NotNull
        var userRole: UserRole)
