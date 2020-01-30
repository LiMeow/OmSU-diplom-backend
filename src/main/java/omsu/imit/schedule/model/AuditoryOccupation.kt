package omsu.imit.schedule.model

import java.sql.Date
import javax.persistence.*


@Entity
@Table(name = "auditory_occupation")
class AuditoryOccupation(@Id
                         @GeneratedValue(strategy = GenerationType.IDENTITY)
                         var id: Int,

                         @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                         @JoinColumn(name = "auditory_id")
                         var auditory: Auditory,

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
                         @JoinTable(name = "auditory_occupation_group",
                                 joinColumns = [JoinColumn(name = "auditory_occupation_id", referencedColumnName = "id")],
                                 inverseJoinColumns = [JoinColumn(name = "group_id", referencedColumnName = "id")])
                         var groups: List<Group>?,

                         @Column
                         var comment: String? = "") {

    constructor(auditory: Auditory,
                timeBlock: TimeBlock,
                day: Day,
                dateFrom: Date,
                dateTo: Date,
                interval: Interval,
                lecturer: Lecturer,
                groups: List<Group>?,
                comment: String?)
            : this(0, auditory, timeBlock, day, dateFrom, dateTo, interval, lecturer, groups, comment)

//    constructor(id: Int,
//                auditory: Auditory,
//                timeFrom: String,
//                timeTo: String,
//                date: String,
//                lecturer: Lecturer,
//                groupId: Int?,
//                comment: String)
//            : this(id, auditory, TimeBlock(timeFrom, timeTo), date, lecturer, null, comment)


//    constructor(
//            auditory: Auditory,
//            timeFrom: String,
//            timeTo: String,
//            date: String,
//            lecturer: Lecturer,
//            comment: String)
//            : this(0, auditory, timeFrom, timeTo, date, lecturer, 0, comment)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuditoryOccupation) return false

        if (id != other.id) return false
        if (auditory != other.auditory) return false
        if (timeBlock != other.timeBlock) return false
        if (day != other.day) return false
        if (dateFrom != other.dateFrom) return false
        if (dateTo != other.dateTo) return false
        if (interval != other.interval) return false
        if (lecturer != other.lecturer) return false
        if (groups != other.groups) return false
        if (comment != other.comment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + auditory.hashCode()
        result = 31 * result + timeBlock.hashCode()
        result = 31 * result + day.hashCode()
        result = 31 * result + dateFrom.hashCode()
        result = 31 * result + dateTo.hashCode()
        result = 31 * result + interval.hashCode()
        result = 31 * result + (lecturer?.hashCode() ?: 0)
        result = 31 * result + (groups?.hashCode() ?: 0)
        result = 31 * result + (comment?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "AuditoryOccupation(" +
                "id=$id, " +
                "timeBlock=$timeBlock, " +
                "day=$day, " +
                "dateFrom='$dateFrom', " +
                "dateTo='$dateTo', " +
                "interval=$interval, " +
                "lecturer=$lecturer, " +
                "groups=$groups, " +
                "comment=$comment)"
    }


}
