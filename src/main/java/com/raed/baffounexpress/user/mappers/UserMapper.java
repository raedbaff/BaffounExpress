package com.raed.baffounexpress.user.mappers;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.raed.baffounexpress.user.DTO.RegisterUserInput;
import com.raed.baffounexpress.user.entities.User;

public class UserMapper {
    public static User RegisterToUser(RegisterUserInput input, PasswordEncoder encoder) {
         User user = new User();
        user.setEmail(input.getEmail());
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setUsername(input.getUsername());
        user.setPassword(encoder.encode(input.getPassword()));
        user.setCountry(input.getCountry());
        user.setCity(input.getCity());
        user.setAddress(input.getAddress());
        user.setZipCode(input.getZipCode());
        user.setPhone(input.getPhone());
        user.setPhoto(input.getPhoto());
        return user;
    }

}
