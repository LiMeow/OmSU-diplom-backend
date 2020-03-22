package omsu.imit.schedule.dto.response

import omsu.imit.schedule.model.Tag

class ClassroomInfo(var id: Int,
                    var buildingNumber: Number,
                    var classroomNumber: String,
                    var tags: List<Tag> = mutableListOf()) {

    override fun toString(): String {
        return "ClassroomInfo(" +
                "id=$id, " +
                "buildingNumber=$buildingNumber, " +
                "classroomNumber='$classroomNumber', " +
                "tags=$tags)"
    }
}
