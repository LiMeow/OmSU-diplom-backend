package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateEventRequest(@get:NotNull var lecturerId: Int,
                              @get:NotNull var required: Boolean,
                              @get:NotEmpty var periods: List<CreateEventPeriodRequest>,
                              var comment: String = "")

