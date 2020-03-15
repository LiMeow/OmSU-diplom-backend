package omsu.imit.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

class CourseInfo(var id: Int,
                 var faculty: String,
                 var startYear: String,
                 var finishYear: String,
                 @JsonInclude(JsonInclude.Include.NON_EMPTY)
                 var groups: List<GroupInfo>? = ArrayList()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CourseInfo) return false

        if (id != other.id) return false
        if (faculty != other.faculty) return false
        if (startYear != other.startYear) return false
        if (finishYear != other.finishYear) return false
        if (groups != other.groups) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + faculty.hashCode()
        result = 31 * result + startYear.hashCode()
        result = 31 * result + finishYear.hashCode()
        result = 31 * result + (groups?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "CourseInfo(" +
                "id=$id, " +
                "faculty='$faculty', " +
                "startYear='$startYear', " +
                "finishYear='$finishYear', " +
                "groups=$groups)"
    }


}