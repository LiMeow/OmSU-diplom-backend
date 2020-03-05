package omsu.imit.schedule.dto.response

class ScheduleInfoForLecturer(
        var semester: Int,
        var studyYear: String,
        var lecturer: LecturerInfo,
        var scheduleItems: MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>>) {
}