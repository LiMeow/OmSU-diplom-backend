package omsu.imit.schedule.dto.response

import java.util.*

class ClassroomInfoByDate(
        var date: Date,
        var classRoom: ClassroomShortInfo,
        var events: MutableCollection<EventInfo>) {

    override fun toString(): String {
        return "ClassroomInfoByDate(" +
                "date=$date, " +
                "classRoom=$classRoom, " +
                "events=$events)"
    }
}