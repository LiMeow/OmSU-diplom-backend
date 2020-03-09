package omsu.imit.schedule.dto.response

class ChairInfo(var id: Int,
                var faculty: String,
                var chair: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ChairInfo) return false

        if (id != other.id) return false
        if (faculty != other.faculty) return false
        if (chair != other.chair) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + faculty.hashCode()
        result = 31 * result + chair.hashCode()
        return result
    }

    override fun toString(): String {
        return "ChairInfo(" +
                "id=$id, " +
                "faculty='$faculty', " +
                "chair='$chair')"
    }


}