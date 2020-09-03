package org.dummy.employee.portal.repository;

import org.dummy.employee.portal.entity.EmployeeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeEntity, Long> {

}


