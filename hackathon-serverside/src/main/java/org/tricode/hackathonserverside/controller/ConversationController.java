package org.tricode.hackathonserverside.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.tricode.hackathonserverside.dto.conversation.ConversationCreateRequestDto;
import org.tricode.hackathonserverside.dto.conversation.ConversationFindRequestDto;
import org.tricode.hackathonserverside.dto.conversation.ConversationResponseDto;
import org.tricode.hackathonserverside.dto.conversation.ConversationResponseForListDto;
import org.tricode.hackathonserverside.model.User;
import org.tricode.hackathonserverside.service.ConversationService;
import org.tricode.hackathonserverside.util.AuthenticationUtil;
import java.util.List;

@Tag(name = "Conversations management", description = "Endpoint for conversation management")
@RequiredArgsConstructor
@RestController
@RequestMapping("/conversation")
public class ConversationController {
    private final ConversationService conversationService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ConversationResponseForListDto> getAllConversations(Authentication authentication) {
        User user = AuthenticationUtil.getAuthenticatedUser(authentication);
        return conversationService.getConversationsList(user.getId());
    }

    @GetMapping("/by-title")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ConversationResponseDto getConversationByTitle(Authentication authentication,
                                                          @Valid @RequestParam String title) {
        User user = AuthenticationUtil.getAuthenticatedUser(authentication);
        return conversationService.getConversationByTitle(user.getId(), title);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ConversationResponseDto createConversation(Authentication authentication,
                                                      @Valid @RequestBody ConversationCreateRequestDto requestDto) {
        User user = AuthenticationUtil.getAuthenticatedUser(authentication);
        return conversationService.createConversation(user.getId(), requestDto);
    }

    @DeleteMapping("/by-title")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void deleteConversationByTitle(Authentication authentication,
                                          @Valid @RequestBody ConversationFindRequestDto requestDto) {
        User user = AuthenticationUtil.getAuthenticatedUser(authentication);
        conversationService.deleteConversation(user.getId(), requestDto.title());
    }
}
