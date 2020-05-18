package omsu.imit.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import omsu.imit.schedule.model.ActivityType
import omsu.imit.schedule.model.Interval
import omsu.imit.schedule.model.TimeBlock
import java.time.LocalDate

class ScheduleItemInfo(
        var id: Int,
        var dateFrom: LocalDate,
        var dateTo: LocalDate,
        var timeBlock: TimeBlock,
        var interval: Interval,
        var buildingNumber: Int,
        var classroomNumber: String,
        var discipline: String,
        var activity: ActivityType,
        var comment: String,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var lecturer: LecturerShortInfo? = null,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        var groups: List<GroupShortInfo> = listOf())