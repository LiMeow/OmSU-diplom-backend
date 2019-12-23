package omsu.imit.schedule.exception

class ScheduleGeneratorException(var errorCode: ErrorCode, vararg params: String) : RuntimeException() {
    override var message: String = String.format(errorCode.message, *params)
}
