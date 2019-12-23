package omsu.imit.schedule.exception

class ErrorItem {
    var errorCode: ErrorCode? = null
    var field: String? = null
    var message: String? = null

    constructor() {}

    constructor(errorCode: ErrorCode) {
        this.errorCode = errorCode
        this.field = errorCode.field
        this.message = errorCode.message
    }

    constructor(ex: ScheduleGeneratorException) {
        this.errorCode = ex.errorCode
        this.field = ex.errorCode!!.field
        this.message = ex.message
    }

    constructor(errorCode: ErrorCode, message: String?) {
        this.errorCode = errorCode
        this.field = errorCode.field
        this.message = message
    }

}
