package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.Day
import omsu.imit.schedule.model.Interval
import java.sql.Date
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class OccupyAuditoryRequest(@get:NotBlank var timeFrom: String,
                                 @get:NotBlank var timeTo: String,
                                 @get:NotNull var day: Day,
                                 @get:NotBlank var dateFrom: Date,
                                 @get:NotBlank var dateTo: Date,
                                 @get:NotNull var interval: Interval,
                                 @get:NotNull var lecturerId: Int,
                                 var groupIds: List<Int>? = null,
                                 @get:NotBlank var comment: String)

