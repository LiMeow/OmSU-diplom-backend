package omsu.imit.schedule.dto.response

class ClassroomShortInfo(var id: Int,
                         var buildingNumber: Int,
                         var classroomNumber: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ClassroomShortInfo) return false

        if (id != other.id) return false
        if (buildingNumber != other.buildingNumber) return false
        if (classroomNumber != other.classroomNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + buildingNumber
        result = 31 * result + classroomNumber.hashCode()
        return result
    }

    override fun toString(): String {
        return "ClassroomShortInfo(" +
                "id=$id, " +
                "buildingNumber=$buildingNumber, " +
                "classroomNumber='$classroomNumber')"
    }


}