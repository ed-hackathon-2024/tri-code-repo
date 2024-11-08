package org.tricode.hackathonserverside.dto.feature;

import jakarta.validation.constraints.NotBlank;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public record UserFeatureAddRequestDto(@NotBlank String question, @NotBlank String answer) {
}
