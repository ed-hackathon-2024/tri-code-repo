package org.tricode.hackathonserverside.mapper;

import org.mapstruct.Mapper;
import org.tricode.hackathonserverside.config.MapperConfig;
import org.tricode.hackathonserverside.dto.conversation.ConversationCreateRequestDto;
import org.tricode.hackathonserverside.dto.conversation.ConversationResponseDto;
import org.tricode.hackathonserverside.dto.conversation.ConversationResponseForListDto;
import org.tricode.hackathonserverside.model.Conversation;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface ConversationMapper {
    Conversation toModel(ConversationCreateRequestDto requestDto);

    ConversationResponseDto toDto(Conversation conversation);

    List<ConversationResponseForListDto> toDtoList(List<Conversation> conversations);
}
