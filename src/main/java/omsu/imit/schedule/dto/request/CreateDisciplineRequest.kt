package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotBlank

data class CreateDisciplineRequest(@get: NotBlank var name: String,
                                   var requirements: List<Int>? = null)