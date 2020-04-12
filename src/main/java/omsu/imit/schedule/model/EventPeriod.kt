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