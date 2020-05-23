package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.ActivityType

class EditScheduleItemRequest(var event: EditEventRequest?,
                              var activityType: ActivityType?,
                              var disciplineId: Int?,
                              var groupIds: List<Int>?)