package omsu.imit.schedule.exception

enum class ErrorCode {
    ACTIVITY_TYPE_NOT_EXISTS("id", "Activity type with id '%s' not exists."),
    ACTIVITY_TYPE_ALREADY_EXISTS("type", "Activity type '%s' already exists."),
    AUDITORY_OCCUPATION_NOT_EXISTS("occupationId", "Auditory occupation with id '%s' not exists."),
    AUDITORY_ALREADY_EXISTS("number", "Auditory '%s' already exists in building '%s'."),
    AUDITORY_NOT_EXISTS("id", "Auditory with id '%s' not exists."),
    BUILDING_ALREADY_EXISTS("id", "Building with hull number '%s' and address '%s' already exists."),
    BUILDING_NOT_EXISTS("id", "Building with id '%s' not exists."),
    CHAIR_NOT_EXISTS("chairId", "Chair with id '%s' not exists"),
    DATABASE_ERROR("database", "Database error"),
    DISCIPLINE_NOT_EXISTS("id", "Discipline with id '%s' not exists."),
    DISCIPLINE_ALREADY_EXISTS("name", "Discipline '%s' already exists."),
    EMPTY_REQUEST_BODY("json", "Empty request body."),
    FACULTY_ALREADY_EXISTS("faculty name", "Faculty with name '%s' in building with address '%s' already exists"),
    FACULTY_NOT_EXISTS("facultyId", "Faculty with id '%s' not exists"),
    GROUP_ALREADY_EXISTS("id", "Group with name '%s' already exists."),
    GROUP_NOT_EXISTS("id", "Group with id '%s' not exists."),
    INTERNAL_SERVER_ERROR("", "'%s'"),
    JSON_PARSE_EXCEPTION("json", "Json parse exception"),
    LECTURER_NOT_EXISTS("lecturerId", "Lecturer with id '%s' not exists."),
    LOGIN_ALREADY_EXISTS("logIn", "User '%s' already exists."),
    STUDY_DIRECTION_NOT_EXISXTS("studyDirectionId", "Study direction with id '%s' not exists."),
    TAG_ALREADY_EXISTS("id", "Tag '%s' already exists."),
    TAG_NOT_EXISTS("id", "Tag with id '%s' not exists."),
    TIMEBLOCK_NOT_EXISTS("id", "TimeBlock '%s'-'%s' not exists."),
    TIMEBLOCK_ALREADY_EXISTS("", "TimeBlock '%s'-'%s' already exists."),
    USER_ALREADY_EXISTS("email", "User with email '%s' already exists."),
    USER_NOT_EXISTS("id", "User with email '%s' not exists."),
    USER_NOT_EXISTS_BY_ID("id", "User with id '%s' not exists."),
    WRONG_OR_EMPTY_FIRST_NAME("firstName", "Wrong or empty firstName."),
    WRONG_OR_EMPTY_LAST_NAME("lastName", "Wrong or empty lastName."),
    WRONG_OR_EMPTY_LOGIN("logIn", "Wrong or empty logIn."),
    WRONG_OR_EMPTY_PASSWORD("password", "Wrong or empty password."),
    WRONG_OR_EMPTY_REQUEST("json", "Wrong or empty request"),
    WRONG_PATRONYMIC("patronymic", "Wrong patronymic."),
    WRONG_PASSWORD("password", "Wrong password."),
    WRONG_URL("url", "Wrong URL");


    var field: String = ""
    var message: String = ""

    constructor() {}

    constructor(field: String, message: String) {
        this.field = field
        this.message = message
    }
}
