package omsu.imit.schedule.dto.request

import java.time.LocalDate
import javax.validation.constraints.NotNull

data class RescheduleEventRequest(
        @get:NotNull var eventPeriodId: Int,
        @get:NotNull var newClassroomId: Int,
        @get:NotNull var newTimeBlockId: Int,
        @get:NotNull var rescheduleFrom: LocalDate,
        @get:NotNull var rescheduleTo: LocalDate) {
}