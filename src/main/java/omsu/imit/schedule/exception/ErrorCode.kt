package omsu.imit.schedule.exception

enum class ErrorCode {
    ACCOUNT_NOT_ACTIVATED("Account for user with email '%s' not activated"),
    ACTIVITY_TYPE_NOT_EXISTS("Activity type with id '%s' not exists."),
    ACTIVITY_TYPE_ALREADY_EXISTS("Activity type '%s' already exists."),
    BAD_REQUEST("%s"),
    BUILDING_ALREADY_EXISTS("Building with hull number '%s' and address '%s' already exists."),
    BUILDING_NOT_EXISTS("Building with id '%s' not exists."),
    CHAIR_NOT_EXISTS("Chair with id '%s' not exists"),
    COURSE_ALREADY_EXISTS("Course with start year '%s' already exists for faculty with id '%s'."),
    COURSE_NOT_EXISTS("Course with id '%s' not exists."),
    CLASSROOM_ALREADY_BUSY("Classroom with id '%s' already busy for timeBlock with id  '%s' on '%s'."),
    CLASSROOM_ALREADY_EXISTS("Classroom '%s' already exists in building '%s'."),
    CLASSROOM_NOT_EXISTS("Classroom with id '%s' not exists."),
    DATABASE_ERROR("Database error"),
    DATE_NOT_INCLUDING_IN_EVENT_PERIOD("Date not including in event period."),
    DISCIPLINE_NOT_EXISTS("Discipline with id '%s' not exists."),
    DISCIPLINE_ALREADY_EXISTS("Discipline '%s' already exists."),
    EMPTY_REQUEST_BODY("Empty request body."),
    EVENT_NOT_EXISTS("Event with id '%s' not exists."),
    EVENT_PERIOD_NOT_EXISTS("Event Period with id '%s' not exists."),
    FACULTY_ALREADY_EXISTS("Faculty with name '%s' in building with address '%s' already exists"),
    FACULTY_NOT_EXISTS("Faculty with id '%s' not exists"),
    GROUP_ALREADY_EXISTS("Group with name '%s' already exists for this course."),
    GROUP_NOT_EXISTS("Group with id '%s' not exists."),
    ONE_OR_MORE_GROUPS_DONT_EXIST,
    LECTURER_NOT_EXISTS("Lecturer with id '%s' not exists."),
    LOGIN_ALREADY_EXISTS("User '%s' already exists."),
    STUDY_DIRECTION_NOT_EXISTS("Study direction with id '%s' not exists."),
    SCHEDULE_ALREADY_EXISTS("Schedule already exists"),
    SCHEDULE_NOT_EXISTS("Schedule with id '%s' not exists"),
    SCHEDULE_NOT_FOUND("Schedule for group with id '%s' %s study year and %s semester not found"),
    SCHEDULE_ITEM_NOT_EXISTS("Schedule item with id '%s' not exists"),
    TAG_ALREADY_EXISTS("Tag '%s' already exists."),
    TAG_NOT_EXISTS("Tag with id '%s' not exists."),
    TOKEN_NOT_FOUND("Verification token broken or expired"),
    TOKEN_EXPIRED("Verification token expired"),
    TIMEBLOCK_NOT_EXISTS("TimeBlock with id '%s' not exists."),
    TIMEBLOCK_ALREADY_EXISTS("TimeBlock '%s'-'%s' already exists."),
    USER_ALREADY_EXISTS("User with email '%s' already exists."),
    USER_NOT_EXISTS("User with email '%s' not exists."),
    USER_WITH_ID_NOT_EXISTS("User with id '%s' not exists."),
    WRONG_PASSWORD("Wrong password."),
    WRONG_URL("Wrong URL");

    var message: String = ""

    constructor() {}

    constructor(message: String) {
        this.message = message
    }
}
