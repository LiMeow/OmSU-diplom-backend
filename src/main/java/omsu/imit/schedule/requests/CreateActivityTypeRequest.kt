package omsu.imit.schedule.requests

import javax.validation.constraints.NotBlank

data class CreateActivityTypeRequest(@get: NotBlank var type: String)