package omsu.imit.schedule.model

enum class Day {
    MONDAY("MONDAY"),
    TUESDAY("TUESDAY"),
    WEDNESDAY("WEDNESDAY"),
    THURSDAY("THURSDAY"),
    FRIDAY("FRIDAY"),
    SATURDAY("SATURDAY"),
    SUNDAY("SUNDAY");

    var description = ""

    constructor() {}

    constructor(description: String) {
        this.description = description
    }
}