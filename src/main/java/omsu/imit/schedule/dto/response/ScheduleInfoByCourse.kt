package omsu.imit.schedule.dto.response

class ScheduleInfoByCourse(
        var course: Int,
        var semester: Int,
        var studyYear: String,
        var schedules: Map<String, ScheduleByCourseElement>) {
}