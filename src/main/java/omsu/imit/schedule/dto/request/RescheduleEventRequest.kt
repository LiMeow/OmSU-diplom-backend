package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.Interval
import java.time.LocalDate
import javax.validation.constraints.NotNull

data class RescheduleEventRequest(
        @get:NotNull var eventPeriodId: Int,
        @get:NotNull var newClassroomId: Int,
        @get:NotNull var newTimeBlockId: Int,
        @get:NotNull var newDateFrom: LocalDate,
        @get:NotNull var newDateTo: LocalDate,
        @get:NotNull var newInterval: Interval) {
}