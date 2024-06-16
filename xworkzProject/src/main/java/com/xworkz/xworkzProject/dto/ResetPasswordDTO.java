package com.xworkz.xworkzProject.dto;

import com.sun.org.apache.xerces.internal.impl.XMLEntityScanner;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPasswordDTO {
   @NotEmpty(message = "Please enter valid password")
    private String password;

   @NotEmpty(message = "Please enter new password")
    private String newPassword;

    @NotEmpty(message = "Please enter email")
    private String email;

    @NotEmpty(message = "Please enter confirm password")
    private String confirmPassword;


}
