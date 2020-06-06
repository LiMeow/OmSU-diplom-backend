package omsu.imit.schedule.model

import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "event_period")
class EventPeriod(@Id
                  @GeneratedValue(strategy = GenerationType.IDENTITY)
                  var id: Int,

                  @ManyToOne(fetch = FetchType.LAZY)
                  @JoinColumn(name = "event_id")
                  var event: Event,

                  @ManyToOne(fetch = FetchType.LAZY)
                  @JoinColumn(name = "classroom_id")
                  var classroom: Classroom,

                  @ManyToOne(fetch = FetchType.LAZY)
                  @JoinColumn(name = "time_block_id")
                  var timeBlock: TimeBlock,

                  @Column
                  @Enumerated(EnumType.STRING)
                  var day: Day,

                  @Column(name = "date_from")
                  var dateFrom: LocalDate,

                  @Column(name = "date_to")
                  var dateTo: LocalDate,

                  @Column
                  @Enumerated(EnumType.STRING)
                  var interval: Interval) {

    constructor(event: Event,
                classroom: Classroom,
                timeBlock: TimeBlock,
                day: Day,
                dateFrom: LocalDate,
                dateTo: LocalDate,
                interval: Interval)
            : this(0, event, classroom, timeBlock, day, dateFrom, dateTo, interval)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EventPeriod) return false

        if (id != other.id) return false
        if (classroom.number != other.classroom.number) return false
        if (timeBlock != other.timeBlock) return false
        if (day.description != other.day.description) return false
        if (dateFrom.toString() != other.dateFrom.toString()) return false
        if (dateTo.toString() != other.dateTo.toString()) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + classroom.hashCode()
        result = 31 * result + timeBlock.hashCode()
        result = 31 * result + day.hashCode()
        result = 31 * result + dateFrom.hashCode()
        result = 31 * result + dateTo.hashCode()
        result = 31 * result + interval.hashCode()
        return result
    }

    override fun toString(): String {
        return "EventPeriod(" +
                "id=$id, " +
                "classroom=$classroom, " +
                "timeBlock=$timeBlock, " +
                "day=$day, " +
                "dateFrom=$dateFrom, " +
                "dateTo=$dateTo, " +
                "interval=$interval)"
    }


}