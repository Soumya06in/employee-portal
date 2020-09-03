package org.dummy.employee.portal.processor;

import org.dummy.employee.portal.bean.Employee;

import java.util.List;

public interface EmployeeService {

    void save(Employee employee);

    List<Employee> listAllEmployee(Integer pageNo, Integer pageSize, String sortBy);

}
