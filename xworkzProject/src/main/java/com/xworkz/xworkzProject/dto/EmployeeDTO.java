package com.xworkz.xworkzProject.dto;

import lombok.*;

import javax.persistence.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString

@Entity
@Table(name = "department_employee")
public class EmployeeDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "name")
    private String name;

    @Column(name = "designation")
    private String designation;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "password")
    private String password;


}
