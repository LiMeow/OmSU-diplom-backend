package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotBlank

data class CreateTimeBlockRequest(@get:NotBlank var timeFrom: String,
                                  @get:NotBlank var timeTo: String)
