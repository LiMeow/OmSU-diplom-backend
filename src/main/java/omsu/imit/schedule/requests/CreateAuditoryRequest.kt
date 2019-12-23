package omsu.imit.schedule.requests

import javax.validation.constraints.NotNull


class CreateAuditoryRequest(@NotNull var buildingId: Int,
                            @NotNull var number: String,
                            var tags: List<Int>? = null)
