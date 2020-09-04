package org.dummy.employee.portal.processor;

import org.dummy.employee.portal.bean.Employee;

import java.util.List;

/**
 * The Interface EmployeeService.
 */
public interface EmployeeService {

    /**
     * Save.
     *
     * @param employee the employee
     */
    void save(Employee employee);

    /**
     * List all employee.
     *
     * @param pageNo the page no
     * @param pageSize the page size
     * @param sortBy the sort by
     * @return the list
     */
    List<Employee> listAllEmployee(Integer pageNo, Integer pageSize, String sortBy);

}
