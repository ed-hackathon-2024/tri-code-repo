package org.tricode.hackathonserverside.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tricode.hackathonserverside.dto.user.UserResponseDto;
import org.tricode.hackathonserverside.service.UserService;
import org.tricode.hackathonserverside.util.AuthenticationUtil;

@Tag(name = "User manager", description = "Endpoint for user editing")
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Get user info",
            description = "Returns user info for user info page")
    public UserResponseDto getUser(Authentication authentication) {
        return userService.getUser(AuthenticationUtil.getAuthenticatedUser(authentication).getId());
    }
}
