package com.raed.baffounexpress.user.DTO;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginUserInput {
    @Length(min = 2, max = 50, message = "Username should be between 2 and 50 characters")
    @NotBlank(message = "Username is required")
    private String username;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$", message = "password must be at least 8 characters long and contain at least one letter and one digit")
    @NotBlank(message = "Password is required")
    private String password;

}
