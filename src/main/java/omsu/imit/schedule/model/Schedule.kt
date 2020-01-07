package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "schedule")
class Schedule(@Id
               @GeneratedValue(strategy = GenerationType.IDENTITY)
               var id: Int,

               @Column
               var course: Int,

               @Column
               var semester: Int,

               @Column(name = "study_year")
               var studyYear: String,

               @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
               @JoinTable(name = "schedule_group",
                       joinColumns = [JoinColumn(name = "schedule_id", referencedColumnName = "id")],
                       inverseJoinColumns = [JoinColumn(name = "group_id", referencedColumnName = "id")])
               var groups: List<Group>?,

               @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
               var scheduleItems: List<ScheduleItem>?) {

    constructor(course: Int, semester: Int, studyYear: String, groups: List<Group>?)
            : this(0, course, semester, studyYear, groups, ArrayList())

    constructor(course: Int, semester: Int, studyYear: String)
            : this(course, semester, studyYear, ArrayList())


}
