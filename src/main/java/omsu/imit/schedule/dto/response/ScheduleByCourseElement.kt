package omsu.imit.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

class ScheduleByCourseElement(
        var group: GroupInfo,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        var schedule: MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>>) {
}