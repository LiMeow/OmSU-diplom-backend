package omsu.imit.schedule.dto.response

import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.Interval
import omsu.imit.schedule.model.TimeBlock
import java.sql.Date

class EventPeriodInfo(var id: Int,
                      var classroom: ClassroomShortInfo,
                      var timeBlock: TimeBlock,
                      var day: Day,
                      var dateFrom: Date,
                      var dateTo: Date,
                      var interval: Interval) {
}