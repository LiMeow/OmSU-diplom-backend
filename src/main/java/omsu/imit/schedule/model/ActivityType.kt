package omsu.imit.schedule.model

enum class ActivityType {
    LECTURE("лекция"),
    PRACTICE("практика"),
    LABORATORY("лабораторная");

    var description = ""

    constructor() {}

    constructor(description: String) {
        this.description = description
    }
}