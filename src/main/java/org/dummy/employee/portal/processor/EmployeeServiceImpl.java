package org.dummy.employee.portal.processor;

import java.util.ArrayList;
import java.util.List;

import org.dummy.employee.portal.bean.Employee;
import org.dummy.employee.portal.entity.EmployeeEntity;
import org.dummy.employee.portal.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class EmployeeServiceImpl.
 */
@Service

/** The Constant log. */
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    /** The employee repository. */
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Save.
     *
     * @param employee the employee
     */
    @Override
    public void save(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        // bean to VO mapping
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeEntity = employeeRepository.save(employeeEntity);
        log.debug("Saved employee with ID: {}", employeeEntity.getId());
    }

    /**
     * List all employee.
     *
     * @param pageNo the page no
     * @param pageSize the page size
     * @param sortBy the sort by
     * @return the list
     */
    @Override
    public List<Employee> listAllEmployee(Integer pageNo, Integer pageSize, String sortBy) {

        List<Employee> employees = new ArrayList<>();
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<EmployeeEntity> pagedResult = employeeRepository.findAll(paging);
        log.debug("Total pages: {} || Total records: {}", pagedResult.getTotalPages(), pagedResult.getTotalElements());
        if (pagedResult.hasContent()) {
            List<EmployeeEntity> employeeEntities = pagedResult.getContent();
            
         // entity to VO mapping
            employeeEntities.forEach(employeeEntity -> {
                Employee employee = new Employee();
                BeanUtils.copyProperties(employeeEntity, employee);
                employees.add(employee);
            });
        }
        return employees;
    }
}
