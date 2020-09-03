package org.dummy.employee.portal.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * The Class Employee.
 */
@JsonInclude(Include.NON_NULL)
@Data
public class Employee {

	public enum Gender{
		MALE, FEMALE
	}

	public enum Department{
		HR, IT, FINANCE
	}

	@NotBlank(message = "First name is mandatory")
	private String firstName;

	@NotBlank(message = "Last name is mandatory")
	private String lastName;

	@NotNull(message = "Gender should be MAL or FEMALE")
	private Gender gender;

	@NotNull(message = "Date of birth is mandatory and of the format yyyy-MM-dd")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date dateOfBirth;

	@NotNull(message = "Department should be any of HR, IT or FINANCE")
	private Department department;

}
