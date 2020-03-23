package omsu.imit.schedule.model

import javax.persistence.*

@Entity
@Table(name = "schedule")
class Schedule(@Id
               @GeneratedValue(strategy = GenerationType.IDENTITY)
               var id: Int,

               @ManyToOne(fetch = FetchType.LAZY)
               @JoinColumn(name = "course_id")
               var course: Course,

               @Column
               var semester: Int,

               @Column(name = "study_year")
               var studyYear: String,

               @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
               var scheduleItems: List<ScheduleItem> = mutableListOf()) {

    constructor(course: Course, semester: Int, studyYear: String)
            : this(0, course, semester, studyYear)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Schedule) return false

        if (id != other.id) return false
        if (course != other.course) return false
        if (semester != other.semester) return false
        if (studyYear != other.studyYear) return false
        if (scheduleItems != other.scheduleItems) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + course.hashCode()
        result = 31 * result + semester
        result = 31 * result + studyYear.hashCode()
        result = 31 * result + scheduleItems.hashCode()
        return result
    }

    override fun toString(): String {
        return "Schedule(" +
                "id=$id, " +
                "course=$course, " +
                "semester=$semester, " +
                "studyYear='$studyYear', " +
                "scheduleItems=$scheduleItems)"
    }


}
