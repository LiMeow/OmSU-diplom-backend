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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EventPeriodInfo) return false

        if (id != other.id) return false
        if (classroom != other.classroom) return false
        if (timeBlock != other.timeBlock) return false
        if (day != other.day) return false
        if (dateFrom != other.dateFrom) return false
        if (dateTo != other.dateTo) return false
        if (interval != other.interval) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (classroom?.hashCode() ?: 0)
        result = 31 * result + timeBlock.hashCode()
        result = 31 * result + day.hashCode()
        result = 31 * result + dateFrom.hashCode()
        result = 31 * result + dateTo.hashCode()
        result = 31 * result + interval.hashCode()
        return result
    }

    override fun toString(): String {
        return "EventPeriodInfo(" +
                "id=$id, " +
                "classroom=$classroom, " +
                "timeBlock=$timeBlock, " +
                "day=$day, " +
                "dateFrom=$dateFrom, " +
                "dateTo=$dateTo, " +
                "interval=$interval)"
    }


}