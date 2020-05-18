package omsu.imit.schedule.dto.response

class EventInfo(var id: Int,
                var lecturer: LecturerShortInfo,
                var comment: String,
                var required: Boolean,
                var eventPeriods: MutableList<EventPeriodInfo> = mutableListOf()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EventInfo) return false

        if (id != other.id) return false
        if (lecturer != other.lecturer) return false
        if (comment != other.comment) return false
        if (required != other.required) return false
        if (eventPeriods != other.eventPeriods) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + lecturer.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + required.hashCode()
        result = 31 * result + eventPeriods.hashCode()
        return result
    }

    override fun toString(): String {
        return "EventInfo(" +
                "id=$id, " +
                "lecturer=$lecturer, " +
                "comment='$comment', " +
                "required=$required, " +
                "eventPeriods=$eventPeriods)"
    }


}
