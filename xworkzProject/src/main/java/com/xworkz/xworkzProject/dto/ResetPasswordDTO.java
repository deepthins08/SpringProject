package com.xworkz.xworkzProject.dto;

import com.sun.org.apache.xerces.internal.impl.XMLEntityScanner;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResetPasswordDTO {
   @NotEmpty(message = "Please enter valid password")
   @Size(min = 16,message = "Please enter 16 characters")
    private String password;

   @NotEmpty(message = "Please enter new password")
   @Size(min = 16,message = "Please enter 16 characters of New Password")
    private String newPassword;

    @NotEmpty(message = "Please enter email")

    private String email;

    @NotEmpty(message = "Please enter confirm password")
    @Size(min = 16,message = "Please enter 16 characters of Confirm Password")
    private String confirmPassword;


}
