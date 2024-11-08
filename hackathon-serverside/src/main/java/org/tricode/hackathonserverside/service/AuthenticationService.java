package org.tricode.hackathonserverside.service;

import org.tricode.hackathonserverside.dto.user.UserLoginRequestDto;
import org.tricode.hackathonserverside.dto.user.UserLoginResponseDto;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto userLoginRequestDto);
}
