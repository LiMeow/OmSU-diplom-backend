package omsu.imit.schedule.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "personal_data")
open class PersonalData(@Id
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        var id: Int,

                        @Column(name = "firstname")
                        var firstName: String,

                        @Column
                        var patronymic: String?,

                        @Column(name = "lastname")
                        var lastName: String,

                        @Column(name = "email")
                        var email: String,

                        @JsonIgnore
                        @Column(name = "password")
                        var password: String?,

                        @Column(name = "user_type")
                        @Enumerated(EnumType.STRING)
                        var userRole: UserRole,

                        @Column
                        var enabled: Boolean) {

    constructor(firstName: String,
                patronymic: String?,
                lastName: String,
                email: String,
                password: String?,
                userRole: UserRole)
            : this(0, firstName, patronymic, lastName, email, password, userRole, false)

    constructor(firstName: String,
                patronymic: String?,
                lastName: String,
                email: String,
                userRole: UserRole)
            : this(firstName, patronymic, lastName, email, null, userRole)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PersonalData) return false

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (patronymic != other.patronymic) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (userRole != other.userRole) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + firstName.hashCode()
        result = 31 * result + (patronymic?.hashCode() ?: 0)
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + userRole.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(" +
                "id=$id, " +
                "firstName='$firstName', " +
                "patronymic=$patronymic, " +
                "lastName='$lastName', " +
                "email='$email', " +
                "password=$password, " +
                "userRole=$userRole)"
    }


}