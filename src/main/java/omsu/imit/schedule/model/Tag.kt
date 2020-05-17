package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "tag")
class Tag(@Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          var id: Int,
          var tag: String) {

    constructor(tag: String) : this(0, tag)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Tag) return false

        if (id != other.id) return false
        if (tag != other.tag) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + tag.hashCode()
        return result
    }
}
