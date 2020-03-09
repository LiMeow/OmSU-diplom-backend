package omsu.imit.schedule.dto.response

class FacultyInfo(var id: Int,
                  var building: BuildingInfo,
                  var name: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FacultyInfo) return false

        if (id != other.id) return false
        if (building != other.building) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + building.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "FacultyInfo(" +
                "id=$id, " +
                "building=$building, " +
                "name='$name')"
    }


}