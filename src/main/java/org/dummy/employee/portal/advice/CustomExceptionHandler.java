package org.dummy.employee.portal.advice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class CustomExceptionHandler.
 */
@ControllerAdvice
@ResponseBody

/** The Constant log. */
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handle method argument not valid.
	 *
	 * @param ex the ex
	 * @param headers the headers
	 * @param status the status
	 * @param request the request
	 * @return the response entity
	 */
	@Override
	protected final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error("Invalid request parameters:", ex);
		String details = ex.getBindingResult().getFieldErrors().parallelStream().map(x -> x.getDefaultMessage())
				.collect(Collectors.joining("; "));

		ErrorResponse error = new ErrorResponse(details, HttpStatus.BAD_REQUEST.name(), occuredAt());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle http message not readable.
	 *
	 * @param ex the ex
	 * @param headers the headers
	 * @param status the status
	 * @param request the request
	 * @return the response entity
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		log.error("Invalid request parameters:", ex);
		ErrorResponse error = new ErrorResponse(ex.getMessage(), status.name(), occuredAt());
		return new ResponseEntity<>(error, status);

	}

	/**
	 * Handle constraint violation.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	protected final ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
			WebRequest request) {

		log.error("Constraint violation error", ex);
		String details = ex.getConstraintViolations().parallelStream().map(x -> x.getMessage())
				.collect(Collectors.joining(", "));

		ErrorResponse error = new ErrorResponse(details, HttpStatus.BAD_REQUEST.name(), occuredAt());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle error.
	 *
	 * @param ex the ex
	 * @param request the request
	 * @return the response entity
	 */
	@ExceptionHandler(Exception.class)
	protected final ResponseEntity<ErrorResponse> handleError(Exception ex, WebRequest request) {

		log.error("Technical error", ex);
		ErrorResponse error = new ErrorResponse("Technical Error - Please contact administrator",
				HttpStatus.INTERNAL_SERVER_ERROR.name(), occuredAt());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Occured at.
	 *
	 * @return the string
	 */
	private String occuredAt() {
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		return dateFormat.format(date);
	}

}
