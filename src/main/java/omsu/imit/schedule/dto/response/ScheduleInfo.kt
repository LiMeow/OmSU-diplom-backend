package omsu.imit.schedule.dto.response

class ScheduleInfo(
        var id: Int,
        var course: CourseInfo,
        var semester: Int,
        var studyYear: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ScheduleInfo) return false

        if (id != other.id) return false
        if (course != other.course) return false
        if (semester != other.semester) return false
        if (studyYear != other.studyYear) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + course.hashCode()
        result = 31 * result + semester
        result = 31 * result + studyYear.hashCode()
        return result
    }
}