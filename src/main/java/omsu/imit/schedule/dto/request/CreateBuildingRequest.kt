package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateBuildingRequest(@get:NotNull var number: Int,
                                 @get:NotBlank var address: String)
