package omsu.imit.schedule.model

import javax.persistence.*


@Entity
@Table(name = "course")
class Course(@Id
             @GeneratedValue(strategy = GenerationType.IDENTITY)
             var id: Int,

             @ManyToOne(fetch = FetchType.EAGER)
             @JoinColumn(name = "faculty_id")
             var faculty: Faculty,

             @Column(name = "start_year")
             var startYear: String,

             @Column(name = "finish_year")
             var finishYear: String,

             @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
             var groups: List<Group>? = ArrayList()) {
    constructor(faculty: Faculty, startYear: String, finishYear: String) :
            this(0, faculty, startYear, finishYear)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Course) return false

        if (id != other.id) return false
        if (faculty != other.faculty) return false
        if (startYear != other.startYear) return false
        if (finishYear != other.finishYear) return false
        if (groups != other.groups) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + faculty.hashCode()
        result = 31 * result + startYear.hashCode()
        result = 31 * result + finishYear.hashCode()
        result = 31 * result + (groups?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Course(" +
                "id=$id, " +
                "faculty=$faculty, " +
                "startYear='$startYear', " +
                "finishYear='$finishYear', " +
                "groups=$groups)"
    }


}
