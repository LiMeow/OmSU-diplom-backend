package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.ActivityType
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CreateScheduleItemRequest(@get:NotNull var event: CreateEventRequest,
                                     @get:NotNull var activityType: ActivityType,
                                     @get:NotNull var disciplineId: Int,
                                     @get:NotEmpty var groupIds: List<Int>) {
}