package omsu.imit.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import omsu.imit.schedule.model.ActivityType

class ScheduleItemInfo(
        var id: Int,
        val eventPeriod: EventPeriodInfo,
        var discipline: String,
        var activity: ActivityType,
        var comment: String,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        var lecturer: LecturerShortInfo? = null,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        var groups: List<GroupShortInfo> = listOf())