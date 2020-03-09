package omsu.imit.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

class GroupInfo(var id: Int,
                var name: String,
                var studyDirection: StudyDirectionInfo,
                var formationYear: String,
                @JsonInclude(JsonInclude.Include.NON_DEFAULT)
                var dissolutionYear: String = "") {
}