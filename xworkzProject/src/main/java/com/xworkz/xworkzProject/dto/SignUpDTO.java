package com.xworkz.xworkzProject.dto;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "signup")
@NamedQuery(name="mail",query = "select count(s.email) from SignUpDTO s where s.email = :email")
@NamedQuery(name="phone",query = "select count(s.phone) from SignUpDTO s where s.phone = :phone")
public class SignUpDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Please Enter First Name")
    @Size(min = 3,max = 30,message = "The First name should be at least 3 to 30 characters")
    @Column(name = "first_name")
    private String firstName;
    @NotNull(message = "Please Enter Last Name")
    @Size(min = 3,max = 30,message = "The Last name should be at least 3 to 30 characters")
    @Column(name = "last_name")
    private String lastName;
    @Email(message = "Please provide valid Email")
    private String email;
    @NotNull(message = "Please enter valid phone")
    @Min(1)
    @Max(9999999999L)
    private Long phone;

    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "modified_by")
    private String modifiedBy;
    @Column(name = "modified_at")
    private LocalDate modifiedAt;

    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public LocalDate getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDate modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignUpDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", created_by='" + createdBy + '\'' +
                ", created_at=" + createdAt +
                ", modified_by='" + modifiedBy + '\'' +
                ", modified_at=" + modifiedAt +
                '}';
    }
}
