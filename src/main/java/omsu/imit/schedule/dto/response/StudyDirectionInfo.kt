package omsu.imit.schedule.dto.response

class StudyDirectionInfo(var id: Int,
                         var faculty: String,
                         var code: String,
                         var name: String,
                         var qualification: String,
                         var studyForm: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StudyDirectionInfo) return false

        if (id != other.id) return false
        if (faculty != other.faculty) return false
        if (code != other.code) return false
        if (name != other.name) return false
        if (qualification != other.qualification) return false
        if (studyForm != other.studyForm) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + faculty.hashCode()
        result = 31 * result + code.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + qualification.hashCode()
        result = 31 * result + studyForm.hashCode()
        return result
    }

    override fun toString(): String {
        return "StudyDirectionInfo(" +
                "id=$id, " +
                "faculty='$faculty', " +
                "code='$code', " +
                "name='$name', " +
                "qualification='$qualification'," +
                " studyForm='$studyForm')"
    }


}