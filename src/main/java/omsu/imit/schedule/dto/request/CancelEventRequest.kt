package omsu.imit.schedule.dto.request

import java.time.LocalDate
import javax.validation.constraints.NotEmpty

data class CancelEventRequest(@get:NotEmpty var dates: List<LocalDate>) {
}