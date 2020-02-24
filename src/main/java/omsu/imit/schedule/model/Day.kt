package omsu.imit.schedule.model

enum class Day {
    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday");

    var description = ""

    constructor() {}

    constructor(description: String) {
        this.description = description
    }
}