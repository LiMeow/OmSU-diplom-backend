package omsu.imit.schedule.dto.response

import omsu.imit.schedule.model.Day

class ScheduleInfo(
        var id: Int,
        var course: Int,
        var semester: Int,
        var studyYear: String,
        var group: GroupInfo,
        var scheduleItems: MutableMap<Day, MutableMap<String, MutableList<ScheduleItemInfo>>>) {
}