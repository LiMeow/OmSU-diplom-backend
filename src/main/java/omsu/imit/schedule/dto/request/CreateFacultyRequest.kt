package omsu.imit.schedule.dto.request

import com.sun.istack.NotNull
import javax.validation.constraints.NotBlank

data class CreateFacultyRequest(@get: NotNull var buildingId: Int,
                                @get: NotBlank var name: String)