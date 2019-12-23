package omsu.imit.schedule.requests

import javax.validation.constraints.NotNull

class CreateTimeBlockRequest(@NotNull var timeFrom: String,
                             @NotNull var timeTo: String)
