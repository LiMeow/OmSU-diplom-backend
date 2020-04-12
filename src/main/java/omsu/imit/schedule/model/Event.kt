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


}
