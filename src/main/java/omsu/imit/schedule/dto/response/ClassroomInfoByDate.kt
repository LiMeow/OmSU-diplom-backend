package omsu.imit.schedule.dto.response

import java.time.LocalDate

class ClassroomInfoByDate(
        var date: LocalDate,
        var classroom: ClassroomInfo,
        var events: MutableCollection<EventInfo>) {

    override fun toString(): String {
        return "ClassroomInfoByDate(" +
                "date=$date, " +
                "classRoom=$classroom, " +
                "events=$events)"
    }
}