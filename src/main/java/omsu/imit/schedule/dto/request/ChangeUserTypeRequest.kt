package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.UserRole
import javax.validation.constraints.NotNull

data class ChangeUserTypeRequest(@get:NotNull var userRole: UserRole)
