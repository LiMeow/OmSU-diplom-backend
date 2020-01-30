package omsu.imit.schedule.exception

enum class ErrorCode {
    ACTIVITY_TYPE_NOT_EXISTS("Activity type with id '%s' not exists."),
    ACTIVITY_TYPE_ALREADY_EXISTS("Activity type '%s' already exists."),
    AUDITORY_OCCUPATION_NOT_EXISTS("Auditory occupation with id '%s' not exists."),
    AUDITORY_ALREADY_BUSY("Auditory with id '%s' already busy for time '%s-%s' on '%s'."),
    AUDITORY_ALREADY_EXISTS("Auditory '%s' already exists in building '%s'."),
    AUDITORY_NOT_EXISTS("Auditory with id '%s' not exists."),
    BUILDING_ALREADY_EXISTS("Building with hull number '%s' and address '%s' already exists."),
    BUILDING_NOT_EXISTS("Building with id '%s' not exists."),
    CHAIR_NOT_EXISTS("Chair with id '%s' not exists"),
    DATABASE_ERROR("Database error"),
    DISCIPLINE_NOT_EXISTS("Discipline with id '%s' not exists."),
    DISCIPLINE_ALREADY_EXISTS("Discipline '%s' already exists."),
    EMPTY_REQUEST_BODY("Empty request body."),
    FACULTY_ALREADY_EXISTS("Faculty with name '%s' in building with address '%s' already exists"),
    FACULTY_NOT_EXISTS("Faculty with id '%s' not exists"),
    GROUP_ALREADY_EXISTS("Group with name '%s' already exists."),
    GROUP_NOT_EXISTS("Group with id '%s' not exists."),
    ONE_OR_MORE_GROUPS_DONT_EXIST,
    LECTURER_NOT_EXISTS("Lecturer with id '%s' not exists."),
    LOGIN_ALREADY_EXISTS("User '%s' already exists."),
    STUDY_DIRECTION_NOT_EXISXTS("Study direction with id '%s' not exists."),
    SCHEDULE_ALREADY_EXISTS("Schedule already exists"),
    SCHEDULE_NOT_EXISTS("Schedule with id '%s' not exists"),
    SCHEDULE_ITEM_NOT_EXISTS("Schedule item with id '%s' not exists"),
    TAG_ALREADY_EXISTS("Tag '%s' already exists."),
    TAG_NOT_EXISTS("Tag with id '%s' not exists."),
    TIMEBLOCK_NOT_EXISTS("TimeBlock '%s'-'%s' not exists."),
    TIMEBLOCK_ALREADY_EXISTS("TimeBlock '%s'-'%s' already exists."),
    USER_ALREADY_EXISTS("User with email '%s' already exists."),
    USER_NOT_EXISTS("User with email '%s' not exists."),
    USER_NOT_EXISTS_BY_ID("User with id '%s' not exists."),
    WRONG_PASSWORD("Wrong password."),
    WRONG_URL("Wrong URL");

    var message: String = ""

    constructor() {}

    constructor(message: String) {
        this.message = message
    }
}
