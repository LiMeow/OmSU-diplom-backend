package omsu.imit.schedule.requests

import javax.validation.constraints.NotNull

class CreateGroupRequest(
        @NotNull var studyDirectionId: Int,
        @NotNull var name: String)
