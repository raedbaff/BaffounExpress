package com.raed.baffounexpress.user.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raed.baffounexpress.exceptions.ConflictException;
import com.raed.baffounexpress.exceptions.ForbiddenException;
import com.raed.baffounexpress.user.DTO.FullUser;
import com.raed.baffounexpress.user.DTO.LoginResponse;
import com.raed.baffounexpress.user.DTO.LoginUserInput;
import com.raed.baffounexpress.user.DTO.RegisterUserInput;
import com.raed.baffounexpress.user.entities.User;
import com.raed.baffounexpress.user.mappers.UserMapper;
import com.raed.baffounexpress.user.repositories.UserRepository;
import com.raed.baffounexpress.user.services.interfaces.UserService;
import com.raed.baffounexpress.utils.JWTUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JWTUtils jwt;

    @Override
    public FullUser registerUser(RegisterUserInput input) {
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new ConflictException("User with email " + input.getEmail() + " Already exists !");
        }
        if (userRepository.existsByUsername(input.getUsername())) {
            throw new ConflictException("User with username " + input.getUsername() + " Already exists !");
        }
        User user = UserMapper.RegisterToUser(input, encoder);
        User saved = userRepository.save(user);
        return FullUser.construct(saved);
    }

    @Override
    public LoginResponse login(LoginUserInput input) {
        Authentication auth = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));
        FullUser user = (FullUser) auth.getPrincipal();
        if (user.isAccountBanned()) {
            throw new ForbiddenException("User "+ user.getUsername() + " is banned !");
        }
        String token = jwt.signJWT(auth);
        String refreshToken = jwt.signRefreshToken(auth);

        LoginResponse response = new LoginResponse(token, refreshToken, user);

        return response;
    }

}
