package omsu.imit.schedule.model

import javax.persistence.*


@Entity
@Table(name = "auditory_occupations")
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
                         var date: String,

                         @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                         @JoinColumn(name = "lecturer_id")
                         var lecturer: Lecturer?,

                         @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
                         @JoinColumn(name = "group_id")
                         var group: Group?,

                         @Column
                         var comment: String?) {

    constructor(auditory: Auditory, timeBlock: TimeBlock, date: String, lecturer: Lecturer?, group: Group?, comment: String)
            : this(0, auditory, timeBlock, date, lecturer, group, comment)

    constructor(id: Int, auditory: Auditory, timeFrom: String, timeTo: String, date: String, lecturer: Lecturer, groupId: Int?, comment: String)
            : this(id, auditory, TimeBlock(timeFrom, timeTo), date, lecturer, null, comment)


    constructor(auditory: Auditory, timeFrom: String, timeTo: String, date: String, lecturer: Lecturer, comment: String)
            : this(0, auditory, timeFrom, timeTo, date, lecturer, 0, comment)


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuditoryOccupation) return false

        if (id != other.id) return false
        if (auditory != other.auditory) return false
        if (timeBlock != other.timeBlock) return false
        if (date != other.date) return false
        if (lecturer != other.lecturer) return false
        if (group != other.group) return false
        if (comment != other.comment) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + auditory.hashCode()
        result = 31 * result + timeBlock.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + (lecturer?.hashCode() ?: 0)
        result = 31 * result + (group?.hashCode() ?: 0)
        result = 31 * result + (comment?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "AuditoryOccupation(" +
                "id=$id, auditory=$auditory, " +
                "timeBlock=$timeBlock, " +
                "date='$date', " +
                "lecturer=$lecturer, " +
                "group=$group, " +
                "comment=$comment)"
    }
}
