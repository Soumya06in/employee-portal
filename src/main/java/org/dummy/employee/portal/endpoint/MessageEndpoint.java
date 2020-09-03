package org.dummy.employee.portal.endpoint;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.dummy.employee.portal.bean.Employee;
import org.dummy.employee.portal.processor.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The Class MessageEndpoint.
 */
@RestController
@Slf4j
public class MessageEndpoint {

	@Autowired
	private EmployeeService employeeService;


	@ApiOperation(value = "Register an employee", response = Employee.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully registered an employee") })
	@PostMapping("/api/v1/employees")
	public ResponseEntity<String> register(@Valid @RequestBody Employee employee) {

		log.debug("Registering employee: {}" , employee.toString());
		employeeService.save(employee);
		log.debug("Successfully registered employee with name: {} {}", employee.getFirstName(), employee.getLastName());
		return new ResponseEntity<>("Successfully registered", HttpStatus.OK);

	}


	@ApiOperation(value = "Get list of all employees", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched the list of employees") })
	@GetMapping("/api/v1/employees")
	public ResponseEntity<List<Employee>> listEmployees( @RequestParam(defaultValue = "0") Integer pageNo,
														 @RequestParam(defaultValue = "10") Integer pageSize,
														 @RequestParam(defaultValue = "firstName") String sortBy) {

		log.debug("Get the list of all employee: [pageNo - {}, pageSize - {}, sortBy - {}]");
		List<Employee> employees = employeeService.listAllEmployee(pageNo, pageSize, sortBy);

		log.debug("Successfully retrieved list of employees with size: {}", employees.size());
		return new ResponseEntity<>(employees, HttpStatus.OK);

	}
	



}
