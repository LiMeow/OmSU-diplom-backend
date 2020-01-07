package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotNull

class OccupyAuditoryRequest(@NotNull var timeFrom: String,
                            @NotNull var timeTo: String,
                            @NotNull var date: String,
                            @NotNull var lecturerId: Int? = 0,
                            var groupIds: List<Int>? = null,
                            @NotNull var comment: String) {

    override fun toString(): String {
        return "OccupyAuditoryRequest(" +
                "timeFrom='$timeFrom', " +
                "timeTo='$timeTo', " +
                "date='$date', " +
                "lecturerId=$lecturerId, " +
                "groupIds=$groupIds, " +
                "comment='$comment')"
    }
}

