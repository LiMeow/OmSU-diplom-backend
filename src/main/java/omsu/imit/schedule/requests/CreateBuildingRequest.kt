package omsu.imit.schedule.requests

import javax.validation.constraints.NotNull

class CreateBuildingRequest(@NotNull var number: Int,
                            @NotNull var address: String)
