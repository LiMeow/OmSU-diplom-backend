package omsu.imit.schedule.response

class AuditoryInfo(var id: Int,
                   var buildingId: Int,
                   var number: String,
                   var tags: List<String>?,
                   var occupationInfo: List<OccupationInfo>?) {

    constructor(id: Int, buildingId: Int, number: String) : this(id, buildingId, number, mutableListOf(), mutableListOf())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuditoryInfo) return false

        if (id != other.id) return false
        if (buildingId != other.buildingId) return false
        if (number != other.number) return false
        if (tags != other.tags) return false
        if (occupationInfo != other.occupationInfo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + buildingId
        result = 31 * result + number.hashCode()
        result = 31 * result + (tags?.hashCode() ?: 0)
        result = 31 * result + (occupationInfo?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "AuditoryInfo(" +
                "id=$id, " +
                "buildingId=$buildingId, " +
                "number='$number', " +
                "tags=$tags, " +
                "occupationInfo=$occupationInfo)"
    }
}
