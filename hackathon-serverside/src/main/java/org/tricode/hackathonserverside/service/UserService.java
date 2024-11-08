package org.tricode.hackathonserverside.service;

import org.tricode.hackathonserverside.dto.user.UserRegistrationRequestDto;
import org.tricode.hackathonserverside.dto.user.UserResponseDto;
import org.tricode.hackathonserverside.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;

    UserResponseDto getUser(Long id);
}
