package omsu.imit.schedule.dto.response

import omsu.imit.schedule.model.Tag

class ClassroomInfo(var id: Int,
                    var buildingNumber: Number,
                    var classroomNumber: String,
                    var tags: List<Tag> = mutableListOf()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ClassroomInfo) return false

        if (id != other.id) return false
        if (buildingNumber != other.buildingNumber) return false
        if (classroomNumber != other.classroomNumber) return false
        if (tags != other.tags) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + buildingNumber.hashCode()
        result = 31 * result + classroomNumber.hashCode()
        result = 31 * result + tags.hashCode()
        return result
    }
}
