package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "schedule_items")
class ScheduleItem(@Id
                   @GeneratedValue(strategy = GenerationType.IDENTITY)
                   var id: Int,

                   @OneToOne(cascade = [CascadeType.ALL])
                   @JoinColumn(name = "auditory_occupation_id", referencedColumnName = "id")
                   var auditoryOccupation: AuditoryOccupation,

                   @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                   @JoinColumn(name = "discipline_id")
                   var discipline: Discipline,

                   @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                   @JoinColumn(name = "activity_type_id")
                   var activityType: ActivityType,

                   @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                   @JoinColumn(name = "schedule_id")
                   var schedule: Schedule) {
}