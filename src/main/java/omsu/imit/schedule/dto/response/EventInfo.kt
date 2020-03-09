package omsu.imit.schedule.dto.response

import java.util.*

class EventInfo(var id: Int,
                var lecturer: String,
                var comment: String,
                var required: Boolean,
                var eventPeriods: List<EventPeriodInfo> = ArrayList()) {

//    constructor(id: Int, timeFrom: String, timeTo: String, date: String, group: GroupInfo, comment: String)
//            : this(id, timeFrom, timeTo, date, null, group, comment)
//
//    constructor(id: Int, timeFrom: String, timeTo: String, date: String, comment: String)
//            : this(id, timeFrom, timeTo, date, null, null, comment)

}
