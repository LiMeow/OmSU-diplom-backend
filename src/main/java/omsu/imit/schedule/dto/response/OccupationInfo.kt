package omsu.imit.schedule.dto.response

import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.Interval

class OccupationInfo(var id: Int,
                     var day: String,
                     var timeFrom: String,
                     var timeTo: String,
                     var dateFrom: String,
                     var dateTo: String,
                     var interval: String,
                     var auditory: AuditoryShortInfo,
                     var lecturer: String,
                     var group: List<GroupInfo>?,
                     var comment: String) {

    constructor(id: Int,
                day: Day, timeFrom: String, timeTo: String,
                dateFrom: String, dateTo: String, interval: Interval,
                auditory: AuditoryShortInfo, lecturer: String, comment: String)
            : this(
            id, day.name, timeFrom, timeTo,
            dateFrom, dateTo, interval.description,
            auditory, lecturer, null, comment)

//    constructor(id: Int, timeFrom: String, timeTo: String, date: String, group: GroupInfo, comment: String)
//            : this(id, timeFrom, timeTo, date, null, group, comment)
//
//    constructor(id: Int, timeFrom: String, timeTo: String, date: String, comment: String)
//            : this(id, timeFrom, timeTo, date, null, null, comment)

}
