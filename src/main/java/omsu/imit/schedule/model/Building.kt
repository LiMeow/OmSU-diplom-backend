package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "building")
open class Building(@Id
                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                    var id: Int,

                    @Column
                    var number: Int,

                    @Column
                    var address: String,

                    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
                    var auditories: List<Auditory>) {

    constructor(id: Int, number: Int, address: String) : this(id, number, address, ArrayList<Auditory>())

    constructor(number: Int, address: String) : this(0, number, address, ArrayList<Auditory>())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Building) return false

        if (id != other.id) return false
        if (number != other.number) return false
        if (address != other.address) return false
        if (auditories != other.auditories) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + number
        result = 31 * result + address.hashCode()
        result = 31 * result + auditories.hashCode()
        return result
    }

    override fun toString(): String {
        return "Building(id=$id, number=$number, address='$address')"
    }


}
