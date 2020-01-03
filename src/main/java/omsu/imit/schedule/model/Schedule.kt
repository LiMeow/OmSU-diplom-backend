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

               @Column(name = "study_form")
               var studyForm: String,

               @Column(name = "study_year")
               var studyYear: String,

               @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
               var scheduleItems: List<ScheduleItem>) {

}
