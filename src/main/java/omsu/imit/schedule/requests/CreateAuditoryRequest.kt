package omsu.imit.schedule.requests

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


data class CreateAuditoryRequest(@get:NotNull var buildingId: Int,
                                 @get:NotBlank var number: String,
                                 var tags: List<Int>? = null)
