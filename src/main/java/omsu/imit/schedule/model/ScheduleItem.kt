package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "schedule_item")
class ScheduleItem(@Id
                   @GeneratedValue(strategy = GenerationType.IDENTITY)
                   var id: Int,

                   @OneToOne(cascade = [CascadeType.ALL])
                   @JoinColumn(name = "auditory_occupation_id", referencedColumnName = "id")
                   var auditoryOccupation: AuditoryOccupation,

                   @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                   @JoinColumn(name = "discipline_id")
                   var discipline: Discipline,

                   @Column(name = "activity_type")
                   @Enumerated(EnumType.STRING)
                   var activityType: ActivityType,

                   @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                   @JoinColumn(name = "schedule_id")
                   var schedule: Schedule) {

    constructor(auditoryOccupation: AuditoryOccupation,
                discipline: Discipline,
                activityType: ActivityType,
                schedule: Schedule)
            : this(0, auditoryOccupation, discipline, activityType, schedule)
}