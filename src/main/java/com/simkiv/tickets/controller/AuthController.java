package com.simkiv.tickets.controller;

import com.simkiv.tickets.dto.JwtAuthenticationResponse;
import com.simkiv.tickets.dto.SignInRequest;
import com.simkiv.tickets.dto.SignUpRequest;
import com.simkiv.tickets.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    @ResponseStatus(OK)
    public JwtAuthenticationResponse signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return authService.authenticateUser(signInRequest);
    }

    @PostMapping("/sign-up")
    @ResponseStatus(OK)
    public Long signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }
}
