package omsu.imit.schedule.model

enum class Qualification {
    BACHELOR("бакалавр"),
    SPECIALIST("специалист"),
    MAGISTER("магистр");

    var qualification: String = ""

    constructor() {}

    constructor(qualification: String) {
        this.qualification = qualification
    }
}