package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "study_directions")
class StudyDirection(@Id
                     @GeneratedValue(strategy = GenerationType.IDENTITY)
                     var id: Int,

                     @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                     @JoinColumn(name = "faculty_id")
                     var faculty: Faculty,

                     @Column
                     var qualification: String,

                     @Column
                     var name: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StudyDirection) return false

        if (id != other.id) return false
        if (faculty != other.faculty) return false
        if (qualification != other.qualification) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + faculty.hashCode()
        result = 31 * result + qualification.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

    override fun toString(): String {
        return "StudyDirection(" +
                "id=$id, " +
                "faculty=$faculty, " +
                "qualification=$qualification, " +
                "name='$name')"
    }


}
