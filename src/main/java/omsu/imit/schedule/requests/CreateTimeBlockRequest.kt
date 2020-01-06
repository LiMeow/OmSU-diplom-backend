package omsu.imit.schedule.requests

import javax.validation.constraints.NotBlank

data class CreateTimeBlockRequest(@get:NotBlank var timeFrom: String,
                                  @get:NotBlank var timeTo: String)
