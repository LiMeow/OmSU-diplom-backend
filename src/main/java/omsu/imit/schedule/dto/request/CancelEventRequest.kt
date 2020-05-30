package omsu.imit.schedule.dto.request

import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class CancelEventRequest(
        @get:NotNull var eventPeriodId: Int,
        @get:NotEmpty var dates: List<LocalDate>) {
}