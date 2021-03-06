package omsu.imit.schedule.dto.response

class ScheduleInfoByGroup(
        var course: Int,
        var semester: Int,
        var studyYear: String,
        var group: GroupInfo,
        var scheduleItems: MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>>) {
}