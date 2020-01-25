package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "chair")
class Chair(@Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            var id: Int = 0,

            @ManyToOne(fetch = FetchType.EAGER)
            @JoinColumn(name = "faculty_id")
            var faculty: Faculty,

            @Column
            var name: String = "",

            @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
            @JoinColumn(name = "chair_id")
            var lecturers: List<Lecturer> = ArrayList()) {

    constructor(faculty: Faculty, name: String) : this(0, faculty, name, mutableListOf())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Chair) return false

        if (id != other.id) return false
        if (faculty != other.faculty) return false
        if (name != other.name) return false
        if (lecturers != other.lecturers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + faculty.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + lecturers.hashCode()
        return result
    }

    override fun toString(): String {
        return "Chair(" +
                "id=$id, " +
                "faculty=$faculty, " +
                "name='$name', " +
                "lecturers=$lecturers)"
    }


}