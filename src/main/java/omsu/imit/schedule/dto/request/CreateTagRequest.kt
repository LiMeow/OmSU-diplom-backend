package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotBlank

data class CreateTagRequest(@get: NotBlank var tag: String)
