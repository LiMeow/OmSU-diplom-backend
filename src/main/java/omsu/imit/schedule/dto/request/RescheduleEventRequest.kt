package omsu.imit.schedule.dto.request

import java.time.LocalDate
import javax.validation.constraints.NotNull

data class RescheduleEventRequest(
        @get:NotNull var classroomId: Int,
        @get:NotNull var timeBlockId: Int,
        @get:NotNull var from: LocalDate,
        @get:NotNull var to: LocalDate) {
}