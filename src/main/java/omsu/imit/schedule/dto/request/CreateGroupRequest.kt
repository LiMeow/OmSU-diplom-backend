package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateGroupRequest(
        @get: NotNull var studyDirectionId: Int,
        @get: NotBlank var name: String)
