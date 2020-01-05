package omsu.imit.schedule.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import javax.validation.constraints.NotNull

/**
 * Configuration reader for Handling errors of exceptions for api users.
 */
@Component
@ConfigurationProperties(prefix = "application.exception.handling.unknown")
class ApiErrorHandlingConfiguration {
    var showDetails: @NotNull Boolean? = null

}