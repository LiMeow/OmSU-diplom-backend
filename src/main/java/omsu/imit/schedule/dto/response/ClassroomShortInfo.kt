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
}