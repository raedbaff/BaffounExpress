package com.raed.baffounexpress.user.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raed.baffounexpress.user.DTO.FullUser;
import com.raed.baffounexpress.user.DTO.LoginResponse;
import com.raed.baffounexpress.user.DTO.LoginUserInput;
import com.raed.baffounexpress.user.DTO.RegisterUserInput;
import com.raed.baffounexpress.user.services.interfaces.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth/")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<FullUser> register(@Valid @RequestBody RegisterUserInput input) {
        FullUser user = userService.registerUser(input);
        ResponseEntity<FullUser> response = ResponseEntity.status(HttpStatus.CREATED).body(user);
        return response;

    }

    @PostMapping("/login")
    public ResponseEntity<FullUser> login(@Valid @RequestBody LoginUserInput input, HttpServletResponse response) {
        LoginResponse loginResponse = userService.login(input);
        Cookie accessCookie = new Cookie("accessToken", loginResponse.getAccessToken());
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(false);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(60 * 60);
        Cookie refreshCookie = new Cookie("refreshToken", loginResponse.getRefreshToken());
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(false);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(60 * 60);
        response.addCookie(refreshCookie);
        response.addCookie(accessCookie);
        FullUser user = loginResponse.getUser();

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
