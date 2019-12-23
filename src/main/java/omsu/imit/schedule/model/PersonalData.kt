package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "personal_datas")
class PersonalData(@Id
                   @GeneratedValue(strategy = GenerationType.IDENTITY)
                   var id: Int,

                   @OneToOne(cascade = [CascadeType.ALL])
                   @JoinColumn(name = "user_id", referencedColumnName = "id")
                   var user: User?,

                   @Column(name = "firstname")
                   var firstName: String,

                   @Column
                   var patronymic: String?,

                   @Column(name = "lastname")
                   var lastName: String) {

    constructor(user: User,
                firstName: String,
                patronymic: String?,
                lastName: String)
            : this(0, user, firstName, patronymic, lastName)

    constructor(id: Int,
                firstName: String,
                patronymic: String?,
                lastName: String)
            : this(id, null, firstName, patronymic, lastName)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PersonalData) return false

        if (id != other.id) return false
        if (user != other.user) return false
        if (firstName != other.firstName) return false
        if (patronymic != other.patronymic) return false
        if (lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + user.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + (patronymic?.hashCode() ?: 0)
        result = 31 * result + lastName.hashCode()
        return result
    }

    override fun toString(): String {
        return "PersonalData(" +
                "id=$id, " +
                "user=$user, " +
                "firstName='$firstName', " +
                "patronymic=$patronymic, " +
                "lastName='$lastName')"
    }


}
