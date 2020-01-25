package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "auditory")
class Auditory(@Id
               @GeneratedValue(strategy = GenerationType.IDENTITY)
               var id: Int,

               @ManyToOne(fetch = FetchType.EAGER)
               @JoinColumn(name = "building_id")
               var building: Building,

               @Column
               var number: String,

               @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
               @JoinTable(name = "auditory_tag",
                       joinColumns = [JoinColumn(name = "auditory_id", referencedColumnName = "id")],
                       inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")])
               var tags: List<Tag>,

               @OneToMany(fetch = FetchType.LAZY, mappedBy = "auditory")
               var auditoryOccupations: List<AuditoryOccupation>?) {

    constructor(id: Int, building: Building, number: String) : this(id, building, number, mutableListOf(), mutableListOf())

    constructor(building: Building, number: String) : this(0, building, number, mutableListOf(), mutableListOf())

//
//    val tagsIds: List<Int>
//        get() {
//            val tagsIds = ArrayList<Int>()
//            for (tag in this.tags) {
//                tagsIds.add(tag.id)
//            }
//            return tagsIds
//        }
//
//    val tagsNames: List<String>
//        get() {
//            val tagsNames = ArrayList<String>()
//            for (tag in this.tags) {
//                tagsNames.add(tag.tag)
//            }
//            return tagsNames
//        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Auditory) return false

        if (id != other.id) return false
        if (building != other.building) return false
        if (number != other.number) return false
        if (tags != other.tags) return false
        if (auditoryOccupations != other.auditoryOccupations) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + building.hashCode()
        result = 31 * result + number.hashCode()
        result = 31 * result + tags.hashCode()
        result = 31 * result + (auditoryOccupations?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Auditory(" +
                "id=$id, " +
                "building=$building, " +
                "number='$number', " +
                "tags=$tags, " +
                "auditoryOccupations=$auditoryOccupations)"
    }


    /*
    fun setOccupationList(occupationList: MutableList<AuditoryOccupation>) {
        for (occupation in occupationList) {
            addOccupation(occupation)
        }
    }


     fun addOccupation(occupation: AuditoryOccupation) {
         if (occupationMap.containsKey(occupation.date))
             occupationMap.get(occupation.date)!!.add(occupation)
         else {
             val occupations = mutableListOf<AuditoryOccupation>()
             occupations.add(occupation)
             occupationMap.put(occupation.date, occupations)
         }
     }*/


}

