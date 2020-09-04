package org.dummy.employee.portal.entity;

import lombok.Data;
import org.dummy.employee.portal.bean.Employee;

import javax.persistence.*;
import java.util.Date;

/**
 * The Class EmployeeEntity.
 */
@Entity
@Table(name = "EMPLOYEE")

/**
 * Instantiates a new employee entity.
 */
@Data
public class EmployeeEntity {

    /** The id. */
    @Id
    @GeneratedValue
    private Long id;

    /** The first name. */
    @Column(nullable = false)
    private String firstName;

    /** The last name. */
    @Column(nullable = false)
    private String lastName;

    /** The gender. */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Employee.Gender gender;

    /** The date of birth. */
    @Column(nullable = false)
    private Date dateOfBirth;

    /** The department. */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Employee.Department department;

}
