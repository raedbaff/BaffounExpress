package com.raed.baffounexpress.user.DTO;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterUserInput {
    @Length(min = 2, max = 50, message = "First name should be between 2 and 50 characters")
    @NotBlank(message = "First name is required")
    private String firstName;
    @Length(min = 2, max = 50, message = "Last name should be between 2 and 50 characters")
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Length(min = 2, max = 50, message = "Username should be between 2 and 50 characters")
    @NotBlank(message = "Username is required")
    private String username;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$", message = "password must be at least 8 characters long and contain at least one letter and one digit")
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotBlank(message = "Email is required")
    private String email;
    private String country;
    private String city;
    private String address;
    private String zipCode;
    private String phone;
    private String photo;

}
