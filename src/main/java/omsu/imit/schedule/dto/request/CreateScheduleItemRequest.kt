package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.ActivityType
import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.Interval
import java.sql.Date
import javax.validation.constraints.NotNull

data class CreateScheduleItemRequest(
        @get:NotNull var day: Day,
        @get:NotNull var dateFrom: Date,
        @get:NotNull var dateTo: Date,
        @get:NotNull var interval: Interval,
        @get:NotNull var timeBlockId: Int,
        @get:NotNull var auditoryId: Int,
        @get:NotNull var disciplineId: Int,
        @get:NotNull var activityType: ActivityType,
        @get:NotNull var lecturerId: Int,
        @get:NotNull var groupId: Int) {
}