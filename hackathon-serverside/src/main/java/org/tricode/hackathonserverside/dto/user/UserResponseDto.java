package org.tricode.hackathonserverside.dto.user;

import java.time.LocalDate;

public record UserResponseDto(Long id,
                              String email,
                              String firstName,
                              String lastName,
                              LocalDate birthDate,
                              String sex) {
}