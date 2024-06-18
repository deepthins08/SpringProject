package com.xworkz.xworkzProject.dto;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
@Entity
@Table(name = "admin")
@NamedQuery(name = "findByMail",query = "select a from AdminDTO a where a.email=:email")
public class AdminDTO {

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

    @Column(name = "failed_attempts_count")
    private int failedAttemptedCount;

//    @Column(name = "failed_attempts_datetime")
//    private LocalDateTime failedAttemptDateTime;


}
