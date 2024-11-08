package org.tricode.hackathonserverside.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tricode.hackathonserverside.dto.message.MessageSendRequestDto;
import org.tricode.hackathonserverside.model.User;
import org.tricode.hackathonserverside.service.ConversationService;
import org.tricode.hackathonserverside.util.AuthenticationUtil;

@Tag(name = "Messages management", description = "Endpoint messaging")
@RequiredArgsConstructor
@RestController
@RequestMapping("/message")
public class MessagingController {
    private final ConversationService conversationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    String sendMessage(Authentication authentication,
                       @Valid @RequestBody MessageSendRequestDto requestDto) {
        User user = AuthenticationUtil.getAuthenticatedUser(authentication);
        return conversationService.sendMessageAndGetAnswer(user.getId(),
                requestDto.conversationTitle(),
                requestDto.message());
    }
}
