package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "discipline")
class Discipline(@Id
                 @GeneratedValue(strategy = GenerationType.IDENTITY)
                 var id: Int,

                 @Column
                 var name: String) {

    constructor(name: String) : this(0, name)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Discipline) return false

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "Discipline(" +
                "id=$id, " +
                "name='$name')"
    }


}
