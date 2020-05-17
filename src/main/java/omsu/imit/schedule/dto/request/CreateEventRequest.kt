package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotNull

data class CreateEventRequest(@get:NotNull var lecturerId: Int,
                              @get:NotNull var required: Boolean,
                              var comment: String = "",
                              var periods: List<CreateEventPeriodRequest> = ArrayList())

