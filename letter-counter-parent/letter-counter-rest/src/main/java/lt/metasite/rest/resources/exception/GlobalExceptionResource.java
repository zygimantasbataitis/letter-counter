package lt.metasite.rest.resources.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import lt.metasite.rest.exceptions.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionResource {

	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse constraintViolationException(ConstraintViolationException ex) {
		return new ApiErrorResponse(500, 5001, ex.getMessage());
	}

	@ExceptionHandler(value = { NoHandlerFoundException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrorResponse noHandlerFoundException(Exception ex) {
		return new ApiErrorResponse(404, 4041, ex.getMessage());
	}

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ApiErrorResponse unknownException(Exception ex) {
		return new ApiErrorResponse(500, 5002, ex.getMessage());
	}

}
