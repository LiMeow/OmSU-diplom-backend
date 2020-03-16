package omsu.imit.schedule.dto.response

class ClassroomsByBuildingInfo(var metaInfo: MetaInfo,
                               var classrooms: List<ClassroomShortInfo>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ClassroomsByBuildingInfo) return false

        if (metaInfo != other.metaInfo) return false
        if (classrooms != other.classrooms) return false

        return true
    }

    override fun hashCode(): Int {
        var result = metaInfo.hashCode()
        result = 31 * result + classrooms.hashCode()
        return result
    }

    override fun toString(): String {
        return "ClassroomsByBuildingInfo(" +
                "metaInfo=$metaInfo, " +
                "classrooms=$classrooms)"
    }


}
