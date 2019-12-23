package omsu.imit.schedule.requests

import javax.validation.constraints.NotNull

class CreateProfessorRequest(@NotNull var lastName: String,
                             @NotNull var firstName: String,
                             @NotNull var patronymic: String)
