package org.tricode.hackathonserverside.service;

import org.tricode.hackathonserverside.dto.conversation.ConversationCreateRequestDto;
import org.tricode.hackathonserverside.dto.conversation.ConversationResponseDto;
import org.tricode.hackathonserverside.dto.conversation.ConversationResponseForListDto;

import java.util.List;

public interface ConversationService {
    ConversationResponseDto createConversation(Long userId,
                                               ConversationCreateRequestDto requestDto);
    void deleteConversation(Long userId, String conversationName);

    List<ConversationResponseForListDto> getConversationsList(Long userId);

    ConversationResponseDto getConversationByTitle(Long userId, String conversationName);

    String sendMessageAndGetAnswer(Long userId,
                                                    String conversationName,
                                                    String message);
}
