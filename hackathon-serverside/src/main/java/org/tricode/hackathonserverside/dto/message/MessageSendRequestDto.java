package org.tricode.hackathonserverside.dto.message;

import jakarta.validation.constraints.NotBlank;

public record MessageSendRequestDto(@NotBlank String conversationTitle, @NotBlank String message) {
}
