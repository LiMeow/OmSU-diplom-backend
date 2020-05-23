package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.Interval
import java.time.LocalDate

class EditEventPeriodRequest(
        var eventPeriodId: Int,
        var classroomId: Int?,
        var timeBlockId: Int?,
        var day: Day?,
        var dateFrom: LocalDate?,
        var dateTo: LocalDate?,
        var interval: Interval?)