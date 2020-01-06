package omsu.imit.schedule.exception

import javax.persistence.EntityNotFoundException

class NotFoundException : EntityNotFoundException {
    constructor(
            errorCode: ErrorCode,
            vararg params: String)
            : super(String.format(errorCode.message, *params))
}