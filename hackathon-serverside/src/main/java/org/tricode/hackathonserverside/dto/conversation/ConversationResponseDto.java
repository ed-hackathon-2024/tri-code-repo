package org.tricode.hackathonserverside.dto.conversation;

import org.tricode.hackathonserverside.dto.message.MessageResponseDto;
import java.util.List;

public record ConversationResponseDto(Long id, String title, List<MessageResponseDto> messages) {
}
