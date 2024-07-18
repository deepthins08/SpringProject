package com.xworkz.xworkzProject.dto;

import lombok.*;

import javax.persistence.*;
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
@Entity
@Table(name = "department")
public class DepartmentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "type")
    private String departmentType;



    @Column(name = "area")
    private String area;


}
