package omsu.imit.schedule.requests

import omsu.imit.schedule.model.UserRole
import javax.validation.constraints.NotNull

open class ChangeUserTypeRequest(@NotNull var userRole: UserRole)
