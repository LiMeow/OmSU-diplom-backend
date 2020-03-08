package omsu.imit.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

class CourseInfo(var id: Int,
                 var faculty: String,
                 var startYear: String,
                 var finishYear: String,
                 @JsonInclude(JsonInclude.Include.NON_EMPTY)
                 var groups: List<GroupInfo>? = ArrayList()) {
}