package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.Interval
import java.sql.Date
import javax.validation.constraints.NotNull


data class CreateEventPeriodRequest(
        @get:NotNull var classroomId: Int,
        @get:NotNull var timeBlockId: Int,
        @get:NotNull var day: Day,
        @get:NotNull var dateFrom: Date,
        @get:NotNull var dateTo: Date,
        @get:NotNull var interval: Interval)