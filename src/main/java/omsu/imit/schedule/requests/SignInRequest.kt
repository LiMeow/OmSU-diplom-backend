package omsu.imit.schedule.requests

import javax.validation.constraints.NotBlank

class SignInRequest(@NotBlank var email: String,
                    @NotBlank var password: String)

