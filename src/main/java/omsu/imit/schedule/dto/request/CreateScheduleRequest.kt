package omsu.imit.schedule.dto.request

import javax.validation.constraints.*

data class CreateScheduleRequest(
        @get:NotNull
        var courseId: Int,

        @get:NotNull
        @get:Min(value = 1, message = "Semester cannot be less than 1")
        @get:Max(value = 10, message = "Semester cannot be more than 10")
        var semester: Int,

        @get:NotBlank
        @get:Pattern(regexp = "^2\\d{3}\\/2\\d{3}\$", message = "Study year must have format 2***/2***")
        var studyYear: String,

        @get:NotNull
        var groupId: Int)