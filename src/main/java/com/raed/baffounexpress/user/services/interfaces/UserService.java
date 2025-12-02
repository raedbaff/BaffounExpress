package com.raed.baffounexpress.user.services.interfaces;

import com.raed.baffounexpress.user.DTO.FullUser;
import com.raed.baffounexpress.user.DTO.LoginResponse;
import com.raed.baffounexpress.user.DTO.LoginUserInput;
import com.raed.baffounexpress.user.DTO.RegisterUserInput;

public interface UserService {
    FullUser registerUser(RegisterUserInput input);
    LoginResponse login(LoginUserInput input);

}
