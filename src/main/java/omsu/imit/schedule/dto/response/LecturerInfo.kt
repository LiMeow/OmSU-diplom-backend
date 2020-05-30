package omsu.imit.schedule.dto.response

class LecturerInfo(var id: Int,
                   var firstName: String,
                   var patronymic: String?,
                   var lastName: String,
                   var chair: ChairInfo) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LecturerInfo) return false

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (patronymic != other.patronymic) return false
        if (lastName != other.lastName) return false
        if (chair != other.chair) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + firstName.hashCode()
        result = 31 * result + patronymic.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + chair.hashCode()
        return result
    }

    override fun toString(): String {
        return "LecturerInfo(" +
                "id=$id, " +
                "firstName='$firstName', " +
                "patronymic='$patronymic', " +
                "lastName='$lastName', " +
                "chair=$chair)"
    }


}