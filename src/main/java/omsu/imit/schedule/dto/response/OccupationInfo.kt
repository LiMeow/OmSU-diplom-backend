package omsu.imit.schedule.dto.response

import omsu.imit.schedule.model.Group

class OccupationInfo(var id: Int,
                     var timeFrom: String,
                     var timeTo: String,
                     var date: String,
                     var occupying: String?,
                     var group: Group?,
                     var comment: String) {

    constructor(id: Int, timeFrom: String, timeTo: String, date: String, occupying: String, comment: String)
            : this(id, timeFrom, timeTo, date, occupying, null, comment)

    constructor(id: Int, timeFrom: String, timeTo: String, date: String, group: Group, comment: String)
            : this(id, timeFrom, timeTo, date, null, group, comment)

    constructor(id: Int, timeFrom: String, timeTo: String, date: String, comment: String)
            : this(id, timeFrom, timeTo, date, null, null, comment)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OccupationInfo) return false

        if (id != other.id) return false
        if (timeFrom != other.timeFrom) return false
        if (timeTo != other.timeTo) return false
        if (date != other.date) return false
        if (occupying != other.occupying) return false
        if (group != other.group) return false
        if (comment != other.comment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + timeFrom.hashCode()
        result = 31 * result + timeTo.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + occupying.hashCode()
        result = 31 * result + (group?.hashCode() ?: 0)
        result = 31 * result + comment.hashCode()
        return result
    }

    override fun toString(): String {
        return "OccupationInfo(" +
                "id=$id, " +
                "timeFrom='$timeFrom', " +
                "timeTo='$timeTo', " +
                "date='$date', " +
                "occupying='$occupying', " +
                "group=$group, " +
                "reason='$comment')"
    }


}
