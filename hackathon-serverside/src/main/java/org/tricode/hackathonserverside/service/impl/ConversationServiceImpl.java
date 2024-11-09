package org.tricode.hackathonserverside.service.impl;

import com.azure.ai.openai.OpenAIClient;
import com.azure.ai.openai.models.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.tricode.hackathonserverside.config.OpenAiConfig;
import org.tricode.hackathonserverside.dto.conversation.ConversationCreateRequestDto;
import org.tricode.hackathonserverside.dto.conversation.ConversationResponseDto;
import org.tricode.hackathonserverside.dto.conversation.ConversationResponseForListDto;
import org.tricode.hackathonserverside.dto.feature.UserFeatureAddRequestDto;
import org.tricode.hackathonserverside.mapper.ConversationMapper;
import org.tricode.hackathonserverside.model.Conversation;
import org.tricode.hackathonserverside.model.Message;
import org.tricode.hackathonserverside.model.User;
import org.tricode.hackathonserverside.repository.ConversationRepository;
import org.tricode.hackathonserverside.repository.UserRepository;
import org.tricode.hackathonserverside.service.ConversationService;
import org.tricode.hackathonserverside.service.UserFeatureService;
import org.tricode.hackathonserverside.util.AiConversationUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final UserRepository userRepository;
    private final UserFeatureService userFeatureService;
    private final ConversationRepository conversationRepository;
    private final ConversationMapper conversationMapper;
    private final OpenAIClient openAIClient;
    private final OpenAiConfig openAiConfig;

    @Override
    public ConversationResponseDto createConversation(Long userId,
                                                      ConversationCreateRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found")
        );
        boolean isConvWithSameName = getConversationsList(userId).stream()
                .anyMatch(conv -> conv.title().equals(requestDto.title()));

        if (isConvWithSameName) {
            throw new EntityExistsException("Entity with title "
                    + requestDto.title() + " already exists");
        }

        Conversation conversation = conversationMapper.toModel(requestDto);
        conversation.setUser(user);
        conversationRepository.save(conversation);

        return conversationMapper.toDto(conversation);
    }

    @Override
    public void deleteConversation(Long userId, String conversationTitle) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found"));
        boolean isRemoved = user.getConversations()
                .removeIf(conversation -> conversation.getTitle().equals(conversationTitle));
        if (!isRemoved) {
            throw new EntityNotFoundException("Conversation with title " + conversationTitle + " not found");
        }
        userRepository.save(user);
    }

    @Override
    public List<ConversationResponseForListDto> getConversationsList(Long userId) {
        List<Conversation> conversationList = conversationRepository.findByUserId(userId);

        return conversationMapper.toDtoList(conversationList);
    }

    @Override
    public ConversationResponseDto getConversationByTitle(Long userId, String conversationTitle) {
        ConversationResponseDto responseDto = conversationMapper
                .toDto(conversationRepository.findByTitleAndUser_Id(conversationTitle, userId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                        "Conversation with title " + conversationTitle
                                                + " not found for user with id " + userId
                                )
                        ));
        return responseDto;
    }

    @Override
    public String sendMessageAndGetAnswer(Long userId, String conversationName,
                                                           String userMessage) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found"));

        Conversation conversation = conversationRepository
                .findByTitleAndUser_Id(conversationName, userId).orElseThrow(
                () -> new EntityNotFoundException("Conversation with title "
                        + conversationName + " not found"));

        List<Message> messageList = conversation.getMessages();

        String lastMessageQuestion = null;

        if (!messageList.isEmpty()) {
            lastMessageQuestion =
                    AiConversationUtil.questionDetector(messageList.get(messageList.size() - 1).getText());
        }

        if (lastMessageQuestion != null) {
            userFeatureService.addFeature(user.getId(),
                    new UserFeatureAddRequestDto(lastMessageQuestion, userMessage));
        }

        List<ChatRequestMessage> chatMessages =
                AiConversationUtil.createConversation(user, conversation);
        chatMessages.add(new ChatRequestUserMessage(userMessage));

        ChatCompletions chatCompletions = openAIClient.getChatCompletions(
                openAiConfig.getModelName(),
                new ChatCompletionsOptions(chatMessages));

        String assistantAnswer = chatCompletions.getChoices().get(0).getMessage().getContent();

        messageList.add(new Message(Message.MessageType.USER, userMessage, conversation));
        messageList.add(new Message(Message.MessageType.ASSISTANT, assistantAnswer, conversation));
        conversation.setMessages(messageList);
        conversationRepository.save(conversation);

        return assistantAnswer;
    }
}
