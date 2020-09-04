package org.dummy.employee.portal.bean;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * The Class Employee.
 */
@JsonInclude(Include.NON_NULL)

/**
 * Instantiates a new employee.
 */
@Data
public class Employee {

	/**
	 * The Enum Gender.
	 */
	public enum Gender {

		/** The male. */
		MALE,
		/** The female. */
		FEMALE
	}

	/**
	 * The Enum Department.
	 */
	public enum Department {

		/** The hr. */
		HR,
		/** The it. */
		IT,
		/** The finance. */
		FINANCE
	}

	/** The first name. */
	@NotBlank(message = "First name is mandatory")
	private String firstName;

	/** The last name. */
	@NotBlank(message = "Last name is mandatory")
	private String lastName;

	/** The gender. */
	@NotNull(message = "Gender should be MAL or FEMALE")
	private Gender gender;

	/** The date of birth. */
	@NotNull(message = "Date of birth is mandatory and of the format yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	/** The department. */
	@NotNull(message = "Department should be any of HR, IT or FINANCE")
	private Department department;

}
