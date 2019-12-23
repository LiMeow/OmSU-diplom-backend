package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "faculties")
class Faculty(@Id
              @GeneratedValue(strategy = GenerationType.IDENTITY)
              var id: Int,

              @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
              @JoinColumn(name = "building_id")
              var building: Building,

              @Column
              var name: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Faculty) return false

        if (id != other.id) return false
        if (building != other.building) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + building.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "Faculty(" +
                "id=$id, " +
                "building=$building, " +
                "name='$name')"
    }


}
