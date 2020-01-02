package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "schedule_headers")
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

               @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
               @JoinTable(name = "schedules",
                       joinColumns = [JoinColumn(name = "schedule_header_id", referencedColumnName = "id")],
                       inverseJoinColumns = [JoinColumn(name = "schedule_item_id", referencedColumnName = "id")])
               var scheduleItems: List<ScheduleItem>) {

}
