package org.dummy.employee.portal.advice;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Instantiates a new error response.
 *
 * @param details the details
 * @param httpStatus the http status
 * @param orccuredAt the orccured at
 */
@RequiredArgsConstructor

/**
 * Gets the orccured at.
 *
 * @return the orccured at
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /** The details. */
    private final String details;
    
    /** The http status. */
    private final String httpStatus;
    
    /** The orccured at. */
    private final String orccuredAt;

}


