package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotBlank

data class DisciplineRequest(@get: NotBlank var name: String)