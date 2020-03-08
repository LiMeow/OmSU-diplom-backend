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
            var dissolutionYear: String? = "",

            @OneToMany(
                    mappedBy = "group",
                    fetch = FetchType.LAZY,
                    cascade = [CascadeType.ALL])
            var schedules: List<Schedule>? = ArrayList()) {

    constructor(studyDirection: StudyDirection,
                course: Course,
                name: String,
                formationYear: String,
                dissolutionYear: String?) :
            this(0, studyDirection, course, name, formationYear, dissolutionYear)

}
