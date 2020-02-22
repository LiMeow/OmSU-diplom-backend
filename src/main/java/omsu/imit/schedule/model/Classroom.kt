package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "classroom")
class Classroom(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                var id: Int,

                @ManyToOne(fetch = FetchType.EAGER)
                @JoinColumn(name = "building_id")
                var building: Building,

                @Column
                var number: String,

                @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                @JoinTable(name = "classroom_tag",
                        joinColumns = [JoinColumn(name = "classroom_id", referencedColumnName = "id")],
                        inverseJoinColumns = [JoinColumn(name = "tag_id", referencedColumnName = "id")])
                var tags: List<Tag>,

                @OneToMany(fetch = FetchType.LAZY, mappedBy = "classroom")
                var events: List<Event>?) {

    constructor(id: Int, building: Building, number: String) : this(id, building, number, mutableListOf(), mutableListOf())

    constructor(building: Building, number: String) : this(0, building, number, mutableListOf(), mutableListOf())

}

