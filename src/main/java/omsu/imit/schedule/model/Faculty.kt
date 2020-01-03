package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "faculties")
class Faculty(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        @JoinColumn(name = "building_id")
        var building: Building? = null,

        @Column
        var name: String = "",

        @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
        @JoinColumn(name = "faculty_id")
        var chairs: List<Chair> = ArrayList()) {

    constructor(building: Building, name: String) : this(0, building, name, ArrayList<Chair>())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Faculty

        if (id != other.id) return false
        if (building != other.building) return false
        if (name != other.name) return false
        if (chairs != other.chairs) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + building.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + chairs.hashCode()
        return result
    }

    override fun toString(): String {
        return "Faculty(" +
                "id=$id, " +
                "building=$building, " +
                "name='$name', " +
                "chairs=$chairs)"
    }


}
