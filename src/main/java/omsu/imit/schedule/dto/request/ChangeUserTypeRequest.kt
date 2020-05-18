package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.Role
import javax.validation.constraints.NotNull

data class ChangeUserTypeRequest(@get:NotNull var role: Role)
