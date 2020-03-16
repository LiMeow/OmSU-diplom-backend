package omsu.imit.schedule.dto.response

class BuildingInfo(var id: Int,
                   var number: Int,
                   var address: String?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BuildingInfo) return false

        if (id != other.id) return false
        if (number != other.number) return false
        if (address != other.address) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + number
        result = 31 * result + (address?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "BuildingInfo(" +
                "id=$id, " +
                "number=$number, " +
                "address=$address)"
    }


}