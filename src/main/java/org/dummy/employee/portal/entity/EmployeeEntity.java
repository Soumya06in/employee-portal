package org.dummy.employee.portal.entity;

import lombok.Data;
import org.dummy.employee.portal.bean.Employee;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EMPLOYEE")
@Data
public class EmployeeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Employee.Gender gender;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Employee.Department department;

}
