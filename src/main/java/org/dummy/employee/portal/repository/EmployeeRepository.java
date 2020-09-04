package org.dummy.employee.portal.repository;

import org.dummy.employee.portal.entity.EmployeeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The Interface EmployeeRepository.
 */
public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeEntity, Long> {

}


