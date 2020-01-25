package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.Interval
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class OccupyAuditoryRequest(@get:NotBlank var timeFrom: String,
                                 @get:NotBlank var timeTo: String,
                                 @get:NotNull var day: Day,
                                 @get:NotBlank var dateFrom: String,
                                 @get:NotBlank var dateTo: String,
                                 @get:NotNull var interval: Interval,
                                 @get:NotNull var lecturerId: Int,
                                 var groupIds: List<Int>? = null,
                                 @get:NotBlank var comment: String)

