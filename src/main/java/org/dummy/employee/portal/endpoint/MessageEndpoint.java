package org.dummy.employee.portal.endpoint;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.dummy.employee.portal.bean.Employee;
import org.dummy.employee.portal.processor.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class MessageEndpoint.
 */
@RestController

/** The Constant log. */
@Slf4j
@Validated
public class MessageEndpoint {

	/** The employee service. */
	@Autowired
	private EmployeeService employeeService;

	/**
	 * Register.
	 *
	 * @param employee the employee
	 * @return the response entity
	 */
	@ApiOperation(value = "Register an employee, dateOfBirth='yyyy-MM-dd', department='HR|IT|FINANCE', gender='MALE|FEMALE'", response = Employee.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully registered an employee") })
	@PostMapping("/api/v1/employees")
	public ResponseEntity<String> register(@Valid @RequestBody Employee employee) {

		log.debug("Registering employee: {}", employee.toString());
		employeeService.save(employee);
		log.debug("Successfully registered employee with name: {} {}", employee.getFirstName(), employee.getLastName());
		return new ResponseEntity<>("Successfully registered", HttpStatus.OK);

	}

	/**
	 * List employees.
	 *
	 * @param pageNo   the page no
	 * @param pageSize the page size
	 * @param sortBy   the sort by
	 * @return the response entity
	 */
	@ApiOperation(value = "Get list of all employees", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched the list of employees") })
	@GetMapping("/api/v1/employees")
	public ResponseEntity<List<Employee>> listEmployees(
			@RequestParam(defaultValue = "0") @Min(value = 0, message = "pageNo should be equal to greater than 0") Integer pageNo,
			@RequestParam(defaultValue = "10") @Min(value = 1, message = "pageSize should be equal to greater than 1") Integer pageSize,
			@RequestParam(defaultValue = "firstName") @Valid @Pattern(regexp = "firstName|lastName", message = "Sorting is allowed by 'firstName' or 'lastName'") String sortBy) {

		log.debug("Get the list of all employee: [pageNo - {}, pageSize - {}, sortBy - {}]", pageNo, pageSize, sortBy);
		List<Employee> employees = employeeService.listAllEmployee(pageNo, pageSize, sortBy);

		log.debug("Successfully retrieved batch of employees with size: {}", employees.size());
		return new ResponseEntity<>(employees, HttpStatus.OK);

	}

}
