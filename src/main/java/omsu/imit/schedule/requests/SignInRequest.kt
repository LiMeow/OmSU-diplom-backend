package omsu.imit.schedule.requests

import javax.validation.constraints.NotNull

class SignInRequest(@NotNull var email: String,
                    @NotNull var password: String)

