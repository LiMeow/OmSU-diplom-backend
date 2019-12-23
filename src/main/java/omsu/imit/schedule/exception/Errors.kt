package omsu.imit.schedule.exception

import java.util.*

class Errors {
    private var errors: MutableList<ErrorItem>? = null


    constructor() {
        this.errors = ArrayList()
    }

    constructor(errors: MutableList<ErrorItem>) {
        this.errors = errors
    }

    fun getErrors(): List<ErrorItem>? {
        return errors
    }

    fun addError(error: ErrorItem) {
        errors!!.add(error)
    }
}
