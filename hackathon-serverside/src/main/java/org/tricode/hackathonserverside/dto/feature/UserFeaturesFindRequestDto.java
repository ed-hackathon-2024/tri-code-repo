package org.tricode.hackathonserverside.dto.feature;

import jakarta.validation.constraints.NotBlank;

public record UserFeaturesFindRequestDto(@NotBlank String question) {
}
