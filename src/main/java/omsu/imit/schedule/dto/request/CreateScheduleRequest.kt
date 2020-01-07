package omsu.imit.schedule.dto.request

import omsu.imit.schedule.model.StudyForm
import javax.validation.constraints.*

data class CreateScheduleRequest(
        @get:NotNull
        @get:Min(value = 1, message = "Course cannot be less than 1")
        @get:Max(value = 5, message = "Course cannot be more than 5")
        var course: Int,

        @get:NotNull
        @get:Min(value = 1, message = "Semester cannot be less than 1")
        @get:Max(value = 10, message = "Semester cannot be more than 10")
        var semester: Int,

        @get:NotBlank var studyForm: StudyForm,

        @get:NotBlank
        @get:Pattern(regexp = "([2]\\d{3}(/)[2]\\d{3})", message = "Study year must have format 2***/2***")
        var studyYear: String,

        @get:NotNull
        var groupIds: List<Int>)