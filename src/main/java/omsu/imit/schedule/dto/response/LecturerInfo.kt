package omsu.imit.schedule.dto.response

class LecturerInfo(var id: Int,
                   var fullName: String,
                   var faculty: String,
                   var enabled: Boolean) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LecturerInfo

        if (id != other.id) return false
        if (fullName != other.fullName) return false
        if (faculty != other.faculty) return false
        if (enabled != other.enabled) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + fullName.hashCode()
        result = 31 * result + faculty.hashCode()
        result = 31 * result + enabled.hashCode()
        return result
    }

    override fun toString(): String {
        return "LecturerInfo(" +
                "id=$id, fullName='$fullName', " +
                "faculty='$faculty', " +
                "enabled=$enabled)"
    }
}