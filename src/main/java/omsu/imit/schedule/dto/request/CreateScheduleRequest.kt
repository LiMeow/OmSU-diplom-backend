package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.StudyDirection
import javax.validation.constraints.NotNull

class CreateScheduleRequest(@NotNull var course: Int,
                            @NotNull var semester: Int,
                            @NotNull var studyDirectionId: StudyDirection,
                            @NotNull var studyYear: String)