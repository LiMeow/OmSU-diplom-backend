package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.UserRole
import javax.validation.constraints.NotNull

open class ChangeUserTypeRequest(@NotNull var userRole: UserRole)
