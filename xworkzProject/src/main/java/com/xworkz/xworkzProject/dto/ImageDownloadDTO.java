package com.xworkz.xworkzProject.dto;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Getter@Setter@AllArgsConstructor@NoArgsConstructor@ToString
@Entity
@Table(name = "image_upload")
public class ImageDownloadDTO {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "image_size")
    private long imageSize;

    @Column(name = "image_type")
    private String imageType;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "status")
    private String status;
}
