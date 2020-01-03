package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "activity_type")
class ActivityType(@Id
                   @GeneratedValue(strategy = GenerationType.IDENTITY)
                   var id: Int,

                   @Column
                   var type: String) {
    constructor(type: String) : this(0, type)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ActivityType) return false

        if (id != other.id) return false
        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + type.hashCode()
        return result
    }

    override fun toString(): String {
        return "ActivityType(" +
                "id=$id, " +
                "type='$type')"
    }


}
