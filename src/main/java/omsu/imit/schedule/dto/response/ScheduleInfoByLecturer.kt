package omsu.imit.schedule.dto.response

class ScheduleInfoByLecturer(
        var semester: Int,
        var studyYear: String,
        var lecturer: LecturerInfo,
        var scheduleItems: MutableMap<String, MutableMap<String, MutableList<ScheduleItemInfo>>>) {
}