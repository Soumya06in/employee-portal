package org.dummy.employee.portal.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.dummy.employee.portal.bean.Employee;
import org.dummy.employee.portal.processor.EmployeeService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class MessageEndpointTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageEndpointTest {

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** The employee service. */
	@MockBean
	private EmployeeService employeeService;

	/** The object mapper. */
	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Test 01 register success.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser(username = "test", password = "testpassword")
	public void test01_register_success() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON)
						.content("{\r\n" + "  \"dateOfBirth\": \"2020-01-20\",\r\n" + "  \"department\": \"HR\",\r\n"
								+ "  \"firstName\": \"string\",\r\n" + "  \"gender\": \"MALE\",\r\n"
								+ "  \"lastName\": \"string\"\r\n" + "}"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Test 02 register fail un authorized.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void test02_register_fail_unAuthorized() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON)
						.content("{\r\n" + "  \"dateOfBirth\": \"2020-01-20\",\r\n" + "  \"department\": \"HR\",\r\n"
								+ "  \"firstName\": \"test_firstName\",\r\n" + "  \"gender\": \"MALE\",\r\n"
								+ "  \"lastName\": \"test_lastName\"\r\n" + "}"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	/**
	 * Test 03 register fail first name null.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser(username = "test", password = "testpassword")
	public void test03_register_fail_firstNameNull() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON)
						.content("{\r\n" + "  \"dateOfBirth\": \"2020-01-20\",\r\n" + "  \"department\": \"HR\",\r\n"
								+ "  \"firstName\": null,\r\n" + "  \"gender\": \"MALE\",\r\n"
								+ "  \"lastName\": \"test_lastName\"\r\n" + "}"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	/**
	 * Test 04 register fail dob null.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser(username = "test", password = "testpassword")
	public void test04_register_fail_dobNull() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON)
						.content("{\r\n" + "  \"dateOfBirth\": null,\r\n" + "  \"department\": \"HR\",\r\n"
								+ "  \"firstName\": \"test_firstName\",\r\n" + "  \"gender\": \"MALE\",\r\n"
								+ "  \"lastName\": \"test_lastName\"\r\n" + "}"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	/**
	 * Test 05 register fail invalid gender and dept. Gender should be either MALE
	 * or FEMALE, Department should be HR, IT or FINANCE
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser(username = "test", password = "testpassword")
	public void test05_register_fail_invalid_genderAndDept() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/v1/employees").contentType(MediaType.APPLICATION_JSON)
						.content("{\r\n" + "  \"dateOfBirth\": null,\r\n" + "  \"department\": \"TEST\",\r\n"
								+ "  \"firstName\": \"test_firstName\",\r\n" + "  \"gender\": \"XXX\",\r\n"
								+ "  \"lastName\": \"test_lastName\"\r\n" + "}"))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	/**
	 * Test 06 list employees success default req params.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser(username = "test", password = "testpassword")
	public void test06_listEmployees_success_defaultReqParams() throws Exception {

		Employee employee1 = new Employee();
		employee1.setFirstName("test1_firstName");

		Employee employee2 = new Employee();
		employee2.setFirstName("test2_firstName");

		List<Employee> employees = new ArrayList<>();
		employees.add(employee1);
		employees.add(employee2);

		Mockito.when(employeeService.listAllEmployee(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(employees);
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/employees").contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		List<Employee> responseEmployees = objectMapper.readValue(responseBody, new TypeReference<List<Employee>>() { });
		assertEquals(2, responseEmployees.size());

	}

	/**
	 * Test 07 list employees fail un-authorized.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void test07_listEmployees_fail_unAuthorized() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees").contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isUnauthorized());

	}

	/**
	 * Test 08 list employees success empty list.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser(username = "test", password = "testpassword")
	public void test08_listEmployees_success_emptyList() throws Exception {

		List<Employee> employees = new ArrayList<>(1);

		Mockito.when(employeeService.listAllEmployee(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString()))
				.thenReturn(employees);
		MvcResult mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/employees").contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		String responseBody = mvcResult.getResponse().getContentAsString();
		List<Employee> responseEmployees = objectMapper.readValue(responseBody, new TypeReference<List<Employee>>() { });
		assertEquals(0, responseEmployees.size());

	}

	/**
	 * Test 08 list employees failure invalid page no.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser(username = "test", password = "testpassword")
	public void test08_listEmployees_failure_invalidPageNo() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/employees?pageNo=-1")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	/**
	 * Test 08 list employees failure invalid page size.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser(username = "test", password = "testpassword")
	public void test08_listEmployees_failure_invalidPageSize() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/employees?pageSize=0")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	/**
	 * Test 09 list employees failure invalid sort param.
	 *
	 * @throws Exception the exception
	 */
	@Test
	@WithMockUser(username = "test", password = "testpassword")
	public void test09_listEmployees_failure_invalidSortParam() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/employees?sortBy=gender")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	

}
