package omsu.imit.schedule.model

import javax.persistence.*


@Entity
@Table(name = "group")
class Group(@Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            var id: Int,

            @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
            @JoinColumn(name = "study_direction_id")
            var studyDirection: StudyDirection,

            @Column
            var name: String) {

    constructor(studyDirection: StudyDirection, name: String) : this(0, studyDirection, name)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Group) return false

        if (id != other.id) return false
        if (studyDirection != other.studyDirection) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + studyDirection.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "Group(" +
                "id=$id, " +
                "studyDirection=$studyDirection, " +
                "name='$name')"
    }


}
