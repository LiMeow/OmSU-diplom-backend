package omsu.imit.schedule.dto.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*

/**
 * Simple status response.
 */
@ApiModel("Status response (ok or failed)")
@JsonInclude(JsonInclude.Include.NON_NULL)
class StatusResponse {
    @ApiModelProperty(value = "status. `failed` for exceptions.", required = true)
    val status: Status
    @ApiModelProperty(value = "error message string", required = true)
    val message: String?

    /**
     * Create response with status.
     *
     * @param status status value
     */
    constructor(status: Status) {
        this.status = status
        message = null
    }

    /**
     * Create failed response with custom message.
     *
     * @param message message text
     */
    constructor(message: String?) {
        status = Status.ERROR
        this.message = message
    }

    /**
     * Create object with status and message.
     *
     * @param status  status value
     * @param message optional message
     */
    @JsonCreator
    constructor(@JsonProperty("status") status: Status, @JsonProperty("message") message: String?) {
        this.status = status
        this.message = message
    }

    /**
     * Represent status of the response.
     */
    enum class Status {
        OK, ERROR;

        @JsonValue
        fun toValue(): String {
            return this.toString().toLowerCase(Locale.getDefault())
        }

        companion object {
            @JsonCreator
            fun forValue(value: String): Status {
                return valueOf(value.toUpperCase(Locale.getDefault()))
            }
        }
    }

    companion object {
        val OK = StatusResponse(Status.OK)
    }
}