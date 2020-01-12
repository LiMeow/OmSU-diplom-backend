package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.Interval
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateScheduleItemRequest(
        @get:NotNull var scheduleId: Int,
        @get:NotNull var day: Day,
        @get:NotBlank var dateFrom: String,
        @get:NotBlank var dateTo: String,
        @get:NotNull var interval: Interval,
        @get:NotNull var timeBlockId: Int,
        @get:NotNull var auditoryId: Int,
        @get:NotNull var disciplineId: Int,
        @get:NotNull var activityTypeId: Int,
        @get:NotNull var lecturerId: Int,
        @get:NotNull var groupId: Int) {
}