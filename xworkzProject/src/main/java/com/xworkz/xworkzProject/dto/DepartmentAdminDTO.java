package com.xworkz.xworkzProject.dto;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
@Entity
@Table(name = "department_admin")
public class DepartmentAdminDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;


}
