package omsu.imit.schedule.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "users")
open class User(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var id: Int,

                @Column(name = "email")
                var email: String,

                @JsonIgnore
                @Column(name = "pswrd")
                var password: String?,

                @Column(name = "user_type")
                @Enumerated(EnumType.STRING)
                var userRole: UserRole) {

    constructor(email: String,
                password: String?,
                userRole: UserRole)
            : this(0, email, password, userRole)

    constructor(email: String,
                userRole: UserRole)
            : this(email, null, userRole)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (id != other.id) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (userRole != other.userRole) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + email.hashCode()
        result = 31 * result + (password?.hashCode() ?: 0)
        result = 31 * result + userRole.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(" +
                "id=$id, " +
                "email='$email', " +
                "password=$password, " +
                "userRole=$userRole)"
    }

}