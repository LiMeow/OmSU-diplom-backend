package omsu.imit.schedule.exception

import omsu.imit.schedule.configuration.ApiErrorHandlingConfiguration
import omsu.imit.schedule.dto.response.FieldValidationFailedResponse
import omsu.imit.schedule.dto.response.FieldValidationFailedResponse.ValidationFailedResponseBuilder
import omsu.imit.schedule.dto.response.StatusResponse
import org.apache.commons.lang3.tuple.Pair
import org.slf4j.LoggerFactory
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.stream.Collectors
import javax.persistence.EntityNotFoundException
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException


/**
 * ApiErrorHandler - controller advice, which handles exceptions and sends correct responses.
 */
@ControllerAdvice
class ApiErrorHandler {

    private val LOGGER = LoggerFactory.getLogger(ApiErrorHandler::class.java)
    private val MESSAGE = "Exception with type: '%s', message: '%s'"
    private val DEFAULT_EXCEPTION_MESSAGE = "Something went wrong! Please try again or contact your administrator"

    private val configuration: ApiErrorHandlingConfiguration? = null

    /**
     * Handles EntityNotFoundException and returns response with according message and http status.
     *
     * @param ex exception object (EntityNotFoundException)
     * @return status object
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseBody
    fun handleEntityNotFoundException(ex: EntityNotFoundException): StatusResponse? {
        val message: String = String.format(MESSAGE, ex.javaClass.simpleName, ex.message)
        LOGGER.warn(message)
        return StatusResponse(StatusResponse.Status.ERROR, ex.message)
    }

    /**
     * Handles MethodArgumentNotValidException and return response with according message and http status.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException): FieldValidationFailedResponse? {
        LOGGER.warn(String.format(MESSAGE, ex.javaClass.simpleName, ex.message))
        val builder = ValidationFailedResponseBuilder()
        val issues = ex.bindingResult
                .fieldErrors
                .stream()
                .map { e: FieldError -> Pair.of(e.field, e.defaultMessage) }
                .collect(Collectors.toList())
        return builder.addIssues(issues).build()
    }

    /**
     * Handles MethodArgumentNotValidException and return response with according message and http status.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseBody
    fun handleValidationException(ex: ConstraintViolationException): FieldValidationFailedResponse? {
        LOGGER.warn(String.format(MESSAGE, ex.javaClass.simpleName, ex.message))
        val builder = ValidationFailedResponseBuilder()
        val issues = ex.constraintViolations
                .stream()
                .map { e: ConstraintViolation<*> -> Pair.of(e.propertyPath.toString(), e.message) }
                .collect(Collectors.toList())
        return builder.addIssues(issues).build()
    }

    /**
     * Handle all database exceptions.
     *
     * @param ex exception object
     * @return status object
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataAccessException::class)
    @ResponseBody
    fun handleDatabaseExceptions(ex: DataAccessException): StatusResponse? {
        val message: String = String.format(MESSAGE, ex.javaClass.simpleName, ex.message)
        LOGGER.warn(message)
        return StatusResponse(StatusResponse.Status.ERROR, ex.message)
    }

    /**
     * Handles CommonValidationException and returns response with according message and http status.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CommonValidationException::class)
    @ResponseBody
    fun handleCommonValidation(ex: CommonValidationException): StatusResponse? {
        LOGGER.warn("Validation error {}", ex.message)
        return StatusResponse(StatusResponse.Status.ERROR, ex.message)
    }

    /**
     * Handle not readable json.
     *
     * @param ex exception object (HttpMessageNotReadableException)
     * @return status object
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseBody
    fun handleJsonParseException(ex: HttpMessageNotReadableException): StatusResponse? {
        LOGGER.warn("Cannot parse json {}", ex.message)
        return StatusResponse(StatusResponse.Status.ERROR, ex.message)
    }

    /**
     * Handles any unknown exception to be user friendly for api consumer.
     *
     * @param ex exception object (Throwable)
     * @return status object
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    @ResponseBody
    fun handleUnknownException(ex: Throwable): StatusResponse? {
        LOGGER.warn(String.format(MESSAGE, ex.javaClass.simpleName, ex.message), ex)
        val message = if (configuration!!.showDetails!!) ex.message else DEFAULT_EXCEPTION_MESSAGE
        return StatusResponse(StatusResponse.Status.ERROR, message)
    }

}
