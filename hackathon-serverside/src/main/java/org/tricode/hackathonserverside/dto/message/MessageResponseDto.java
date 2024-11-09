package org.tricode.hackathonserverside.dto.message;

import org.tricode.hackathonserverside.model.Message;

public record MessageResponseDto(String text, Message.MessageType type) {
}
