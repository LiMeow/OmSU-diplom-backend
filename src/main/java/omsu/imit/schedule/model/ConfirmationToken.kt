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
}