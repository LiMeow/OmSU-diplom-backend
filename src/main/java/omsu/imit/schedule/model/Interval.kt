package omsu.imit.schedule.model

enum class Interval {
    ONLY_EVEN_WEEKS("Только чётные недели"),
    ONLY_ODD_WEEKS("Только нечётные недели"),
    EVERY_WEEK("Каждуюю неделю");

    var description = ""

    constructor() {}

    constructor(description: String) {
        this.description = description
    }
}