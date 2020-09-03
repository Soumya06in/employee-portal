package org.dummy.employee.portal.processor;

import lombok.extern.slf4j.Slf4j;
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

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void save(Employee employee) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        BeanUtils.copyProperties(employee, employeeEntity);
        employeeEntity = employeeRepository.save(employeeEntity);
        log.debug("Saved employee with ID: {}", employeeEntity.getId());
    }

    @Override
    public List<Employee> listAllEmployee(Integer pageNo, Integer pageSize, String sortBy) {

        List<Employee> employees = new ArrayList<>();
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<EmployeeEntity> pagedResult = employeeRepository.findAll(paging);
        log.debug("Total pages: {} || Total records: {}", pagedResult.getTotalPages(), pagedResult.getTotalElements());
        if (pagedResult.hasContent()) {
            List<EmployeeEntity> employeeEntities = pagedResult.getContent();

            employeeEntities.forEach(employeeEntity -> {
                Employee employee = new Employee();
                BeanUtils.copyProperties(employeeEntity, employee);
                employees.add(employee);
            });
        }
        return employees;
    }
}
