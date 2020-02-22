package omsu.imit.schedule.dto.response

import java.util.*

class ScheduleItemInfo(
        var id: Int,
        var dateFrom: Date,
        var dateTo: Date,
        var interval: String,
        var buildingNumber: Int,
        var classroom: String,
        var lecturer: String,
        var groups: List<String>,
        var discipline: String,
        var activity: String,
        var comment: String) {

    override fun toString(): String {
        return "ScheduleItemInfo(" +
                "id=$id, " +
                "dateFrom=$dateFrom, " +
                "dateTo=$dateTo, " +
                "interval='$interval', " +
                "buildingNumber=$buildingNumber, " +
                "classroom='$classroom', " +
                "lecturer='$lecturer', " +
                "groups=$groups, " +
                "discipline='$discipline', " +
                "activity='$activity', " +
                "comment='$comment')"
    }
}