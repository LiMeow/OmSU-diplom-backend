package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "schedule_item")
class ScheduleItem(@Id
                   @GeneratedValue(strategy = GenerationType.IDENTITY)
                   var id: Int,

                   @OneToOne(fetch = FetchType.LAZY)
                   @JoinColumn(name = "event_id", referencedColumnName = "id")
                   var event: Event,

                   @ManyToOne(fetch = FetchType.LAZY)
                   @JoinColumn(name = "discipline_id")
                   var discipline: Discipline,

                   @Column(name = "activity_type")
                   @Enumerated(EnumType.STRING)
                   var activityType: ActivityType,

                   @ManyToMany(fetch = FetchType.LAZY)
                   @JoinTable(name = "schedule_item_group",
                           joinColumns = [JoinColumn(name = "schedule_item_id", referencedColumnName = "id")],
                           inverseJoinColumns = [JoinColumn(name = "group_id", referencedColumnName = "id")])
                   var groups: List<Group>,

                   @ManyToOne(fetch = FetchType.LAZY)
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ScheduleItem) return false

        if (id != other.id) return false
        if (event != other.event) return false
        if (discipline != other.discipline) return false
        if (activityType != other.activityType) return false
        if (groups != other.groups) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + event.hashCode()
        result = 31 * result + discipline.hashCode()
        result = 31 * result + activityType.hashCode()
        result = 31 * result + groups.hashCode()
        return result
    }


}