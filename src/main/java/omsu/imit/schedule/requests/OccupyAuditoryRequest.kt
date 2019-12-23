package omsu.imit.schedule.requests

import javax.validation.constraints.NotNull

class OccupyAuditoryRequest(@NotNull var timeFrom: String,
                            @NotNull var timeTo: String,
                            @NotNull var date: String,
                            @NotNull var lecturerId: Int? = 0,
                            var groupId: Int? = 0,
                            @NotNull var comment: String) {
    override fun toString(): String {
        return "OccupyAuditoryRequest(timeFrom='$timeFrom', timeTo='$timeTo', date='$date', lecturerId=$lecturerId, groupId=$groupId, comment='$comment')"
    }
}

