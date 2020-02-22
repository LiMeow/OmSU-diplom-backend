package omsu.imit.schedule.dto.response

class ScheduleForLecturer(var lecturerInfo: LecturerInfo,
                          var scheduleItems: List<ScheduleItemInfo>) {

    override fun toString(): String {
        return "ScheduleForLecturer(" +
                "lecturerInfo=$lecturerInfo, " +
                "scheduleItems=$scheduleItems)"
    }
}