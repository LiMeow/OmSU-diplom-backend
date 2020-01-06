package omsu.imit.schedule.exception

/**
 * Collects all errors regarding data validation. Without field specific details.
 */
class CommonValidationException : RuntimeException {
    constructor(errorCode: ErrorCode, vararg params: String) : super(String.format(errorCode.message, *params))
    constructor(s: String) : super(s) {}
    constructor(s: String?, exc: Exception?) : super(s, exc) {}
}