package omsu.imit.schedule.dto.response

class LecturerShortInfo(var id: Int,
                        var firstName: String,
                        var patronymic: String?,
                        var lastName: String,
                        var email: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LecturerShortInfo) return false

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (patronymic != other.patronymic) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + firstName.hashCode()
        result = 31 * result + (patronymic?.hashCode() ?: 0)
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        return result
    }

    override fun toString(): String {
        return "LecturerShortInfo(" +
                "id=$id, " +
                "firstName='$firstName', " +
                "patronymic=$patronymic, " +
                "lastName='$lastName', " +
                "email='$email')"
    }


}