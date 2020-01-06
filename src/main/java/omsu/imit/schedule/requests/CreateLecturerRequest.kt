package omsu.imit.schedule.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateLecturerRequest(@get:NotNull var charId: Int,
                                 @get:NotBlank var firstName: String,
                                 var patronymic: String?,
                                 @get:NotBlank var lastName: String,
                                 @get:NotBlank var email: String)