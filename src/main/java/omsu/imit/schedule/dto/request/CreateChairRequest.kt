package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateChairRequest(@get:NotNull var facultyId: Int,
                              @get:NotBlank var name: String)