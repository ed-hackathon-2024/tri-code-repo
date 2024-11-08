package org.tricode.hackathonserverside.mapper;

import org.mapstruct.Mapper;
import org.tricode.hackathonserverside.config.MapperConfig;
import org.tricode.hackathonserverside.dto.user.UserRegistrationRequestDto;
import org.tricode.hackathonserverside.dto.user.UserResponseDto;
import org.tricode.hackathonserverside.model.User;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto requestDto);

    UserResponseDto toDto(User user);
}
