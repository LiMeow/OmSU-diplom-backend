package omsu.imit.schedule.requests

import javax.validation.constraints.NotBlank

data class CreateTagRequest(@get: NotBlank var tag: String)
