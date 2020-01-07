package omsu.imit.schedule.dto.response

import omsu.imit.schedule.model.Tag

class AuditoryInfo(var id: Int,
                   var number: String,
                   var tags: List<Tag>,
                   var occupations: List<OccupationInfo>?) {

    constructor(id: Int, number: String) : this(id, number, mutableListOf(), mutableListOf())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuditoryInfo) return false

        if (id != other.id) return false
        if (number != other.number) return false
        if (tags != other.tags) return false
        if (occupations != other.occupations) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + number.hashCode()
        result = 31 * result + (tags?.hashCode() ?: 0)
        result = 31 * result + (occupations?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "AuditoryInfo(" +
                "id=$id, " +
                "number='$number', " +
                "tags=$tags, " +
                "occupationInfo=$occupations)"
    }
}
