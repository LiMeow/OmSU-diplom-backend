package omsu.imit.schedule.dto.request

import javax.validation.constraints.Pattern

class EditTimeBlockRequest(@get:Pattern(regexp = "^(([0,1][0-9])|(2[0-3])):[0-5][0-9]\$", message = "Time must have format hh:mm")
                           var timeFrom: String? = null,

                           @get:Pattern(regexp = "^(([0,1][0-9])|(2[0-3])):[0-5][0-9]\$", message = "Time must have format hh:mm")
                           var timeTo: String? = null)
