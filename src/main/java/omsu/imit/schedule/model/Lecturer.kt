package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "lecturer")
class Lecturer(@Id
               @GeneratedValue(strategy = GenerationType.IDENTITY)
               var id: Int,

               @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
               @JoinColumn(name = "chair_id")
               var chair: Chair,

               @OneToOne(cascade = [CascadeType.ALL])
               @JoinColumn(name = "user_id", referencedColumnName = "id")
               var user: User,

               @OneToMany(fetch = FetchType.LAZY, mappedBy = "lecturer")
               var preferences: List<LecturerPreferences>?) {

    constructor(id: Int,
                chair: Chair,
                user: User) :
            this(id, chair, user, null)

    constructor(chair: Chair,
                user: User) :
            this(0, chair, user)


    fun getFullName(): String {
        return user.firstName[0] + "." +
                user.patronymic!![0] + "." +
                user.lastName;
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Lecturer) return false

        if (id != other.id) return false
        if (chair != other.chair) return false
        if (user != other.user) return false
        if (preferences != other.preferences) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + chair.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + (preferences?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Lecturer(" +
                "id=$id, " +
                "chair=$chair, " +
                "personalData=$user, " +
                "preferences=$preferences)"
    }


}
