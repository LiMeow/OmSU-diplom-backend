package omsu.imit.schedule.exception

import org.hibernate.exception.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.multipart.support.MissingServletRequestPartException
import org.springframework.web.servlet.NoHandlerFoundException
import java.util.*

@ControllerAdvice
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseBody
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): Errors {
        val errors = Errors()
        ex.bindingResult.fieldErrors.forEach { fieldError ->
            val errorCode = Arrays.stream(ErrorCode.values()).filter { e -> e.field == fieldError.field && e.name.startsWith("WRONG") }.findFirst().get()
            errors.addError(ErrorItem(errorCode))
        }
        return errors
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseBody
    fun handleConstraintViolation(ex: ConstraintViolationException): Errors {
        val errors = Errors()
        errors.addError(ErrorItem(ErrorCode.USER_ALREADY_EXISTS, ex.message))
        return errors
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ScheduleGeneratorException::class)
    @ResponseBody
    fun handleError(ex: ScheduleGeneratorException): Errors {
        val errors = Errors()
        errors.addError(ErrorItem(ex))
        return errors
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseBody
    fun handleHttpMessageNotReadable(): Errors {
        val errors = Errors()
        errors.addError(ErrorItem(ErrorCode.WRONG_OR_EMPTY_REQUEST))
        return errors
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
            MissingServletRequestParameterException::class,
            MissingServletRequestPartException::class,
            IllegalArgumentException::class)
    @ResponseBody
    fun handleMissingServletRequest(): Errors {
        val errors = Errors()
        errors.addError(ErrorItem(ErrorCode.WRONG_OR_EMPTY_REQUEST))
        return errors
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException::class)
    @ResponseBody
    fun handleNoHandlerFound(): Errors {
        val errors = Errors()
        errors.addError(ErrorItem(ErrorCode.WRONG_URL))
        return errors
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    @ResponseBody
    fun handleThrowable(ex: Throwable): Errors {
        val errors = Errors()
        errors.addError(ErrorItem(ErrorCode.INTERNAL_SERVER_ERROR, ex.message))
        return errors
    }

}
