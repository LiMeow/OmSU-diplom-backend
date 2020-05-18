package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "discipline")
class Discipline(@Id
                 @GeneratedValue(strategy = GenerationType.IDENTITY)
                 var id: Int,

                 @Column
                 var name: String,

                 @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                 @JoinTable(name = "discipline_requirements",
                         joinColumns = [JoinColumn(name = "discipline_id", referencedColumnName = "id")],
                         inverseJoinColumns = [JoinColumn(name = "requirement_id", referencedColumnName = "id")])
                 var requirements: List<Tag> = mutableListOf()) {

    constructor(name: String) : this(0, name)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Discipline) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (requirements != other.requirements) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + requirements.hashCode()
        return result
    }
}
