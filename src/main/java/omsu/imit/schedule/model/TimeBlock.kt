package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "time_block")
class TimeBlock(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var id: Int,
                @Column(name = "time_from")
                var timeFrom: String,
                @Column(name = "time_to")
                var timeTo: String) {

    constructor(timeFrom: String, timeTo: String) : this(0, timeFrom, timeTo)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TimeBlock) return false

        if (id != other.id) return false
        if (timeFrom != other.timeFrom) return false
        if (timeTo != other.timeTo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + timeFrom.hashCode()
        result = 31 * result + timeTo.hashCode()
        return result
    }

    override fun toString(): String {
        return "TimeBlock(" +
                "id=$id, " +
                "timeFrom='$timeFrom', " +
                "timeTo='$timeTo')"
    }
}
