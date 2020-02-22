package omsu.imit.schedule.model

import java.sql.Date
import javax.persistence.*


@Entity
@Table(name = "event")
class Event(@Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            var id: Int,

            @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
            @JoinColumn(name = "classroom_id")
            var classroom: Classroom,

            @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
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
            var interval: Interval,

            @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
            @JoinColumn(name = "lecturer_id")
            var lecturer: Lecturer,

            @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
            @JoinTable(name = "event_group",
                    joinColumns = [JoinColumn(name = "event_id", referencedColumnName = "id")],
                    inverseJoinColumns = [JoinColumn(name = "group_id", referencedColumnName = "id")])
            var groups: List<Group>?,

            @Column
            var comment: String? = "") {

    constructor(classroom: Classroom,
                timeBlock: TimeBlock,
                day: Day,
                dateFrom: Date,
                dateTo: Date,
                interval: Interval,
                lecturer: Lecturer,
                groups: List<Group>?,
                comment: String?)
            : this(0, classroom, timeBlock, day, dateFrom, dateTo, interval, lecturer, groups, comment)


}
