package org.dummy.employee.portal.exceptions;

/**
 * The Class BusinessException.
 */
public class BusinessException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new business exception.
	 *
	 * @param message the message
	 * @param th the th
	 */
	public BusinessException(String message, Throwable th) {
		super(message, th);
	}
	
	/**
	 * Instantiates a new business exception.
	 *
	 * @param message the message
	 */
	public BusinessException(String message) {
		super(message);
	}
	

}
