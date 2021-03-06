package omsu.imit.schedule.dto.request

import javax.validation.constraints.NotNull

class CreateActivityRequest(@NotNull var disciplineId: Int,
                            @NotNull var activityTypeId: Int,
                            @NotNull var lecturerId: Int,
                            @NotNull var groupId: Int)