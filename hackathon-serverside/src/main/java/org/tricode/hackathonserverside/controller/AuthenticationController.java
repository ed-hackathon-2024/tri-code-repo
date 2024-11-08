package org.tricode.hackathonserverside.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tricode.hackathonserverside.dto.user.UserLoginRequestDto;
import org.tricode.hackathonserverside.dto.user.UserLoginResponseDto;
import org.tricode.hackathonserverside.dto.user.UserRegistrationRequestDto;
import org.tricode.hackathonserverside.dto.user.UserResponseDto;
import org.tricode.hackathonserverside.exception.RegistrationException;
import org.tricode.hackathonserverside.service.AuthenticationService;
import org.tricode.hackathonserverside.service.UserService;

@Tag(name = "Authentication manager", description = "Endpoint for authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/registration")
    @Operation(summary = "Register user", description = "User registration. You need to put user "
            + "as request body.")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "User login. You need to put user "
            + "as request body.")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}