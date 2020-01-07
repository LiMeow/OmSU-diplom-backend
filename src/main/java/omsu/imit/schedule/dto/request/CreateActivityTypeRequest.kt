package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotBlank

data class CreateActivityTypeRequest(@get: NotBlank var type: String)