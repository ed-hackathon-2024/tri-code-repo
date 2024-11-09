package org.tricode.hackathonserverside.dto.feature;

import jakarta.validation.constraints.NotBlank;

public record UserFeatureEditRequestDto(@NotBlank String question, @NotBlank String answer) {
}
