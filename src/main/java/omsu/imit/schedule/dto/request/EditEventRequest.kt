package omsu.imit.schedule.dto.request

class EditEventRequest(var lecturerId: Int?,
                       var required: Boolean?,
                       var periods: List<EditEventPeriodRequest>?,
                       var comment: String?)