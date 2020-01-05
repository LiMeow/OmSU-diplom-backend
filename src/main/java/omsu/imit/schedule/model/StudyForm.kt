package omsu.imit.schedule.model

enum class StudyForm {
    FULL_TIME("очная"),
    PART_TIME("очно-заочная"),
    SELF_STUDY("заочная");

    var studyForm: String = ""

    constructor() {}

    constructor(studyForm: String) {
        this.studyForm = studyForm
    }
}