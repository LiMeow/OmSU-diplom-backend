package omsu.imit.schedule.dto.request

import javax.validation.constraints.*

class CreateScheduleRequest(@NotNull
                            @Min(value = 1, message = "Course cannot be less than 1")
                            @Max(value = 5, message = "Course cannot be more than 5")
                            var course: Int,

                            @NotNull
                            @Min(value = 1, message = "Semester cannot be less than 1")
                            @Max(value = 10, message = "Semester cannot be more than 10")
                            var semester: Int,

                            @NotNull var studyDirectionId: Int,

                            @NotBlank
                            @Pattern(regexp = "([12]\\d{3}(/)[12]\\d{3})")
                            var studyYear: String)