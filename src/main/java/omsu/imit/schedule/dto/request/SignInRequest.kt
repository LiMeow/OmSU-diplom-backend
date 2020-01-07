package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotBlank

class SignInRequest(@NotBlank var email: String,
                    @NotBlank var password: String)

