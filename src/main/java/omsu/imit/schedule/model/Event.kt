package omsu.imit.schedule.model

import javax.persistence.*


@Entity
@Table(name = "event")
class Event(@Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            var id: Int,

            @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
            @JoinColumn(name = "lecturer_id")
            var lecturer: Lecturer,

            @Column
            var comment: String = "",

            @Column
            var required: Boolean,

            @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
            var eventPeriods: List<EventPeriod> = mutableListOf()) {

    constructor(lecturer: Lecturer,
                comment: String,
                required: Boolean)
            : this(0, lecturer, comment, required)


    override fun toString(): String {
        return "Event(" +
                "id=$id, " +
                "lecturer=${lecturer.getFullName()}, " +
                "comment='$comment'," +
                "required=$required, " +
                "eventPeriods=$eventPeriods)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Event) return false

        if (id != other.id) return false
        if (lecturer != other.lecturer) return false
        if (comment != other.comment) return false
        if (required != other.required) return false
        if (eventPeriods != other.eventPeriods) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + lecturer.hashCode()
        result = 31 * result + comment.hashCode()
        result = 31 * result + required.hashCode()
        result = 31 * result + eventPeriods.hashCode()
        return result
    }


}
