package omsu.imit.schedule.dto.response

class ScheduleByCourseElement(
        var group: GroupInfo,
        var schedule: MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>>) {
}