package org.tricode.hackathonserverside.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.tricode.hackathonserverside.dto.feature.UserFeatureAddRequestDto;
import org.tricode.hackathonserverside.dto.feature.UserFeatureEditRequestDto;
import org.tricode.hackathonserverside.dto.feature.UserFeatureResponseDto;
import org.tricode.hackathonserverside.dto.feature.UserFeaturesFindRequestDto;
import org.tricode.hackathonserverside.service.UserFeatureService;
import org.tricode.hackathonserverside.util.AuthenticationUtil;

import java.util.List;

@Tag(name = "User features management", description = "Endpoint for user features management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/features")
public class UserFeaturesController {
    private final UserFeatureService userFeatureService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<UserFeatureResponseDto> getUserFeatures(Authentication authentication) {
        return userFeatureService
                .getUsersFeatures(AuthenticationUtil.getAuthenticatedUser(authentication).getId());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserFeatureResponseDto addFeature(Authentication authentication,
                                             @Valid @RequestBody UserFeatureAddRequestDto requestDto) {
        return userFeatureService.addFeature(
                AuthenticationUtil.getAuthenticatedUser(authentication).getId(), requestDto);
    }

    @PostMapping("/add-list")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void addFeaturesList(Authentication authentication,
                                @RequestBody @Valid List<UserFeatureAddRequestDto> requestDtoList) {
        userFeatureService.addFeatureList(AuthenticationUtil.getAuthenticatedUser(authentication).getId(),
                        requestDtoList);
    }

    @DeleteMapping("/by-question")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteUserFeaturesByQuestion(Authentication authentication,
                                             @Valid @RequestBody UserFeaturesFindRequestDto requestDto) {
        userFeatureService.deleteFeatureByQuestion(
                AuthenticationUtil.getAuthenticatedUser(authentication).getId(), requestDto.question());
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserFeatureResponseDto updateUserFeatures(Authentication authentication,
                                                     @Valid @RequestBody UserFeatureEditRequestDto requestDto) {
        return userFeatureService.updateFeature(AuthenticationUtil.getAuthenticatedUser(authentication).getId(), requestDto);
    }
}
