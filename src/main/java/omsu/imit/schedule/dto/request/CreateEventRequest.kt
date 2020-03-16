package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotNull

data class CreateEventRequest(@get:NotNull var lecturerId: Int,
                              var comment: String = "",
                              @get:NotNull var required: Boolean,
                              var periods: List<CreateEventPeriodRequest> = ArrayList())

