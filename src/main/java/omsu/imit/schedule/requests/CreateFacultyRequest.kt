package omsu.imit.schedule.requests

import com.sun.istack.NotNull

class CreateFacultyRequest(
        @NotNull var buildingId: Int,
        @NotNull var name: String)