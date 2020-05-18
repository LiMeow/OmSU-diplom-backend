package omsu.imit.schedule.dto.response

import omsu.imit.schedule.dto.response.StatusResponse.Status;
import org.apache.commons.lang3.tuple.Pair


import java.util.*
import java.util.function.Consumer

/**
 * Response which used for field validation exception preparing.
 */
class FieldValidationFailedResponse(val status: Status, val issues: Map<String, MutableList<String>>) {

    /**
     * Assistable builder of status.
     */
    class ValidationFailedResponseBuilder {
        private val issues: MutableMap<String, MutableList<String>> = HashMap()
        private fun addIssue(field: String, issue: String): ValidationFailedResponseBuilder {
            issues.computeIfAbsent(field) { k: String? -> ArrayList() }.add(issue)
            return this
        }

        fun addIssues(issues: List<Pair<String?, String?>>): ValidationFailedResponseBuilder {
            issues.forEach(Consumer { i: Pair<String?, String?> -> addIssue(i.key!!, i.value!!) })
            return this
        }

        fun build(): FieldValidationFailedResponse {
            val status: Status = if (issues.isEmpty()) Status.OK else Status.ERROR
            return FieldValidationFailedResponse(status, issues)
        }
    }

}