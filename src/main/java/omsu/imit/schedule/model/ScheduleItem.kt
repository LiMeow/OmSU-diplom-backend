package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "schedule_item")
class ScheduleItem(@Id
                   @GeneratedValue(strategy = GenerationType.IDENTITY)
                   var id: Int,

                   @OneToOne(cascade = [CascadeType.ALL])
                   @JoinColumn(name = "event_id", referencedColumnName = "id")
                   var event: Event,

                   @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                   @JoinColumn(name = "discipline_id")
                   var discipline: Discipline,

                   @Column(name = "activity_type")
                   @Enumerated(EnumType.STRING)
                   var activityType: ActivityType,

                   @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                   @JoinColumn(name = "schedule_id")
                   var schedule: Schedule) {

    constructor(event: Event,
                discipline: Discipline,
                activityType: ActivityType,
                schedule: Schedule)
            : this(0, event, discipline, activityType, schedule)
}