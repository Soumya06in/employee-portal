package org.dummy.employee.portal.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

/**
 *
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status, WebRequest request) {
        log.error("Constraint error:", ex);
        String details = ex.getBindingResult().getFieldErrors()
                .parallelStream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ErrorResponse error = new ErrorResponse(details, HttpStatus.BAD_REQUEST.name(), occuredAt());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleError(Exception ex, WebRequest request) {

        log.error("Technical error", ex);
        ErrorResponse error = new ErrorResponse("Technical Error - Please contact administrator",
                HttpStatus.INTERNAL_SERVER_ERROR.name(), occuredAt());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String occuredAt() {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(date);
    }

}
