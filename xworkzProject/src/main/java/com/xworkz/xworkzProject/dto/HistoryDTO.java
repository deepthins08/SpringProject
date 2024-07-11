package com.xworkz.xworkzProject.dto;

import lombok.*;

import javax.persistence.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
@Entity
@Table(name = "history")
public class HistoryDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int historyId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "complaint_id")
    private Integer complaintId;

    @Column(name = "department_id")
    private Integer departmentId;

    @Column(name = "status")
    private String status;

}
