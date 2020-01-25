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

            @Column
            var name: String,

            @OneToMany(
                    mappedBy = "group",
                    fetch = FetchType.LAZY,
                    cascade = [CascadeType.ALL])
            var schedules: List<Schedule>) {

    constructor(studyDirection: StudyDirection, name: String) : this(0, studyDirection, name, ArrayList<Schedule>())

}
