package omsu.imit.schedule.dto.response

class LecturerInfo(var id: Int,
                   var fullName: String,
                   var chair: ChairInfo) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LecturerInfo) return false

        if (id != other.id) return false
        if (fullName != other.fullName) return false
        if (chair != other.chair) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + fullName.hashCode()
        result = 31 * result + chair.hashCode()
        return result
    }

    override fun toString(): String {
        return "LecturerInfo(" +
                "id=$id, " +
                "fullName='$fullName', " +
                "chair=$chair)"
    }


}