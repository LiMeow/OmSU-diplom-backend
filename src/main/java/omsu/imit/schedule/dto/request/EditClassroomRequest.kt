package omsu.imit.schedule.dto.request

class EditClassroomRequest(var number: String? = null,
                           var tags: List<Int>? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EditClassroomRequest) return false

        if (number != other.number) return false
        if (tags != other.tags) return false

        return true
    }

    override fun hashCode(): Int {
        var result = number?.hashCode() ?: 0
        result = 31 * result + (tags?.hashCode() ?: 0)
        return result
    }
}
