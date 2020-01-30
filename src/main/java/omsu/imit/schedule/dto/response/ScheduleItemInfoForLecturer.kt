package omsu.imit.schedule.dto.response

import java.util.*

class ScheduleItemInfoForLecturer(
        var day: String,
        var timeFrom: String,
        var timeTo: String,
        var dateFrom: Date,
        var dateTo: Date,
        var interval: String,
        var buildingNumber: Int,
        var auditory: String,
        var groups: List<String>,
        var discipline: String,
        var activity: String,
        var comment: String) {
}