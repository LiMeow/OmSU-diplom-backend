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

                   @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                   @JoinTable(name = "schedule_item_group",
                           joinColumns = [JoinColumn(name = "schedule_item_id", referencedColumnName = "id")],
                           inverseJoinColumns = [JoinColumn(name = "group_id", referencedColumnName = "id")])
                   var groups: List<Group>,

                   @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                   @JoinColumn(name = "schedule_id")
                   var schedule: Schedule) {

    constructor(event: Event,
                discipline: Discipline,
                activityType: ActivityType,
                groups: List<Group>,
                schedule: Schedule)
            : this(0, event, discipline, activityType, groups, schedule)

    override fun toString(): String {
        return "ScheduleItem(" +
                "id=$id, " +
                "event=$event, " +
                "discipline=${discipline.name}, " +
                "activityType=${activityType.name}, " +
                "groups=$groups)"
    }


}