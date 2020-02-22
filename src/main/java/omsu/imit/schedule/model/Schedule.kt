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

               @ManyToOne(fetch = FetchType.LAZY)
               @JoinColumn(name = "group_id")
               var group: Group,

               @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
               var scheduleItems: List<ScheduleItem>) {

    constructor(course: Int, semester: Int, studyYear: String, group: Group)
            : this(0, course, semester, studyYear, group, ArrayList())
}
