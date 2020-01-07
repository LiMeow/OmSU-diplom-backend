package omsu.imit.schedule.model

enum class Qualification {
    BACHELOR("бакалавр"),
    SPECIALIST("специалист"),
    MAGISTER("магистр");

    var description = ""

    constructor() {}

    constructor(description: String) {
        this.description = description
    }
}