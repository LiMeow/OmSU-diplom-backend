package omsu.imit.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonInclude

class GroupInfo(var id: Int,
                var name: String,
                var studyDirection: StudyDirectionInfo,
                var formationYear: String,
                @JsonInclude(JsonInclude.Include.NON_DEFAULT)
                var dissolutionYear: String? = "") {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GroupInfo) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (studyDirection != other.studyDirection) return false
        if (formationYear != other.formationYear) return false
        if (dissolutionYear != other.dissolutionYear) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + studyDirection.hashCode()
        result = 31 * result + formationYear.hashCode()
        result = 31 * result + (dissolutionYear?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "GroupInfo(" +
                "id=$id, " +
                "name='$name', " +
                "studyDirection=$studyDirection, " +
                "formationYear='$formationYear', " +
                "dissolutionYear=$dissolutionYear)"
    }


}