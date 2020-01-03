package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "chairs")
class Chair(@Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            var id: Int,

//            @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
//            @JoinColumn(name = "faculty_id")
//            var faculty: Faculty,

            @Column
            var name: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Chair) return false

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
        return "Chair(" +
                "id=$id, " +
                "name='$name')"
    }


}