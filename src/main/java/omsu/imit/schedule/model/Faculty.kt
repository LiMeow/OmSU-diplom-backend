package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "faculty")
class Faculty(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int,

        @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JoinColumn(name = "building_id")
        var building: Building,

        @Column
        var name: String,

        @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY)
        var chairs: List<Chair> = listOf()) {

    constructor(building: Building, name: String) : this(0, building, name)

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
