package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "classroom")
class Classroom(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var id: Int,

                @ManyToOne(fetch = FetchType.LAZY)
                @JoinColumn(name = "building_id")
                var building: Building,

                @Column
                var number: String,

                @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                @JoinTable(name = "classroom_tag",
                        joinColumns = [JoinColumn(name = "classroom_id", referencedColumnName = "id")],
                        inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")])
                var tags: List<Tag> = mutableListOf()) {

    constructor(building: Building, number: String) : this(0, building, number)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Classroom) return false

        if (id != other.id) return false
        if (building != other.building) return false
        if (number != other.number) return false
        if (tags != other.tags) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + building.hashCode()
        result = 31 * result + number.hashCode()
        result = 31 * result + tags.hashCode()
        return result
    }

}

