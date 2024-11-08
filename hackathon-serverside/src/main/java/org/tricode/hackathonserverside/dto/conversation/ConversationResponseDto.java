package org.tricode.hackathonserverside.dto.conversation;

import org.tricode.hackathonserverside.model.Message;
import java.util.List;

public record ConversationResponseDto(Long id, String title, List<Message> messages) {
}
