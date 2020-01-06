package omsu.imit.schedule.requests

import javax.validation.constraints.NotBlank

data class DisciplineRequest(@get: NotBlank var name: String)