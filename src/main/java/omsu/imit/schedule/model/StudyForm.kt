package omsu.imit.schedule.model

enum class StudyForm {
    FULL_TIME("очная"),
    PART_TIME("очно-заочная"),
    SELF_STUDY("заочная");

    var form: String = ""

    constructor() {}

    constructor(studyForm: String) {
        this.form = studyForm
    }
}