package omsu.imit.schedule.requests

import javax.validation.constraints.NotNull

class CreateLecturerRequest(@NotNull var charId: Int,
                            @NotNull var firstName: String,
                            @NotNull var patronymic: String?,
                            @NotNull var lastName: String,
                            @NotNull var email: String)