package omsu.imit.schedule.dto.response

import java.time.LocalDate

class ClassroomInfoByDate(
        var dateFrom: LocalDate,
        var dateTo: LocalDate,
        var classroom: ClassroomInfo,
        var events: MutableCollection<EventInfo>) {
    
}