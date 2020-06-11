package omsu.imit.schedule.dto.response

class ScheduleInfoByCourse(
        var scheduleId: Int,
        var course: Int,
        var semester: Int,
        var studyYear: String,
        var schedule: Map<String, ScheduleByCourseElement>) {
}