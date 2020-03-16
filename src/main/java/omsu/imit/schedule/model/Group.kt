package omsu.imit.schedule.model

import javax.persistence.*


@Entity
@Table(name = "`group`")
class Group(@Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            var id: Int,

            @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
            @JoinColumn(name = "study_direction_id")
            var studyDirection: StudyDirection,

            @ManyToOne(fetch = FetchType.LAZY)
            @JoinColumn(name = "course_id")
            var course: Course,

            @Column
            var name: String,

            @Column(name = "formation_year")
            var formationYear: String,

            @Column(name = "dissolution_year")
            var dissolutionYear: String? = null,

            @OneToMany(
                    mappedBy = "group",
                    fetch = FetchType.LAZY,
                    cascade = [CascadeType.ALL])
            var schedules: List<Schedule> = ArrayList()) {

    constructor(studyDirection: StudyDirection,
                course: Course,
                name: String,
                formationYear: String) :
            this(0, studyDirection, course, name, formationYear, null)

    constructor(studyDirection: StudyDirection,
                course: Course,
                name: String,
                formationYear: String,
                dissolutionYear: String?) :
            this(0, studyDirection, course, name, formationYear, dissolutionYear)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Group) return false

        if (id != other.id) return false
        if (studyDirection != other.studyDirection) return false
        if (course != other.course) return false
        if (name != other.name) return false
        if (formationYear != other.formationYear) return false
        if (dissolutionYear != other.dissolutionYear) return false
        if (schedules != other.schedules) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + studyDirection.hashCode()
        result = 31 * result + course.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + formationYear.hashCode()
        result = 31 * result + (dissolutionYear?.hashCode() ?: 0)
        result = 31 * result + schedules.hashCode()
        return result
    }


}
