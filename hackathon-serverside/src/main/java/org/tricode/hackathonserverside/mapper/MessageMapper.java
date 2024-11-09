package org.tricode.hackathonserverside.mapper;

import org.mapstruct.Mapper;
import org.tricode.hackathonserverside.config.MapperConfig;
import org.tricode.hackathonserverside.dto.message.MessageResponseDto;
import org.tricode.hackathonserverside.model.Message;
import java.util.List;

@Mapper(config = MapperConfig.class)
public interface MessageMapper {
    MessageResponseDto toDto(Message message);

    List<MessageResponseDto> toDto(List<Message> messages);
}
