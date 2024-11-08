package org.tricode.hackathonserverside.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record UserRegistrationRequestDto(@NotBlank @Email String email,
                                         @NotBlank @Length(min = 8, max = 20) String password,
                                         @NotBlank String firstName, @NotBlank String lastName,
                                         @NotNull @PastOrPresent LocalDate birthDate,
                                         @NotBlank String sex) {
}
