package com.xworkz.xworkzProject.dto;

import lombok.*;

import javax.persistence.*;
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
@Entity
@Table(name = "department")
public class DepartmentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "type")
    private String departmentType;

    @Column(name = "location")
    private String location;

    @Column(name = "no_of_employes")
    private int noOfEmployees;
}
