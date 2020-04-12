package omsu.imit.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.Interval
import omsu.imit.schedule.model.TimeBlock
import java.time.LocalDate

class EventPeriodInfo(var id: Int,
                      @JsonInclude(JsonInclude.Include.NON_NULL)
                      var classroom: ClassroomShortInfo?,
                      var timeBlock: TimeBlock,
                      var day: Day,
                      var dateFrom: LocalDate,
                      var dateTo: LocalDate,
                      var interval: Interval) {
}