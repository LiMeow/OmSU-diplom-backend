package omsu.imit.schedule.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "`user`")
class User(@Id
           @GeneratedValue(strategy = GenerationType.IDENTITY)
           var id: Int = 0,

           @Column(name = "firstname")
           var firstName: String = "",

           @Column
           var patronymic: String? = "",

           @Column(name = "lastname")
           var lastName: String = "",

           @Column(name = "email")
           var email: String = "",

           @JsonIgnore
           @Column(name = "password")
           var password: String? = "",

           @Column(name = "user_type")
           @Enumerated(EnumType.STRING)
           var role: Role = Role.ROLE_USER,

           @Column
           var enabled: Boolean = false) {

    constructor(firstName: String,
                patronymic: String?,
                lastName: String,
                email: String,
                password: String?,
                role: Role)
            : this(0, firstName, patronymic, lastName, email, password, role, false)

    constructor(firstName: String,
                patronymic: String?,
                lastName: String,
                email: String,
                role: Role)
            : this(firstName, patronymic, lastName, email, null, role)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (patronymic != other.patronymic) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (role != other.role) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + firstName.hashCode()
        result = 31 * result + (patronymic?.hashCode() ?: 0)
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + role.hashCode()
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
                "userRole=$role)"
    }


}