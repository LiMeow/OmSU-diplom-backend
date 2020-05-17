package omsu.imit.schedule.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "confirmation_token")
class ConfirmationToken(@Id
                        @GeneratedValue(strategy = GenerationType.IDENTITY)
                        var id: Int,

                        @Column
                        var token: String,

                        @OneToOne(fetch = FetchType.LAZY)
                        @JoinColumn(nullable = false, name = "user_id")
                        var user: User) {

    constructor(user: User) : this(0, UUID.randomUUID().toString(), user)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ConfirmationToken) return false

        if (id != other.id) return false
        if (token != other.token) return false
        if (user != other.user) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + token.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }

    override fun toString(): String {
        return "ConfirmationToken(id=$id, token='$token', user=$user)"
    }


}