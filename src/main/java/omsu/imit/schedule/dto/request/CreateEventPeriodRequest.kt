package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.Interval
import java.time.LocalDate
import javax.validation.constraints.NotNull


data class CreateEventPeriodRequest(
        @get:NotNull var classroomId: Int,
        @get:NotNull var timeBlockId: Int,
        @get:NotNull var dateFrom: LocalDate,
        @get:NotNull var dateTo: LocalDate,
        @get:NotNull var interval: Interval)