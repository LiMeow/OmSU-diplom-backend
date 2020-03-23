package omsu.imit.schedule.model

import java.sql.Date
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
                  var dateFrom: Date,

                  @Column(name = "date_to")
                  var dateTo: Date,

                  @Column
                  @Enumerated(EnumType.STRING)
                  var interval: Interval) {

    constructor(event: Event,
                classroom: Classroom,
                timeBlock: TimeBlock,
                day: Day,
                dateFrom: Date,
                dateTo: Date,
                interval: Interval)
            : this(0, event, classroom, timeBlock, day, dateFrom, dateTo, interval)


}