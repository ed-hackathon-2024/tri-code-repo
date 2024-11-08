package org.tricode.hackathonserverside.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tricode.hackathonserverside.dto.user.UserRegistrationRequestDto;
import org.tricode.hackathonserverside.dto.user.UserResponseDto;
import org.tricode.hackathonserverside.exception.RegistrationException;
import org.tricode.hackathonserverside.mapper.UserMapper;
import org.tricode.hackathonserverside.model.Role;
import org.tricode.hackathonserverside.model.User;
import org.tricode.hackathonserverside.repository.RoleRepository;
import org.tricode.hackathonserverside.repository.UserRepository;
import org.tricode.hackathonserverside.service.UserService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private Role userRole;

    @PostConstruct
    public void init() {
        userRole = roleRepository.findRoleByRoleName(Role.RoleName.ROLE_USER).orElseThrow(
                () -> new EntityNotFoundException("Can`t find role with name: "
                        + Role.RoleName.ROLE_USER.name())
        );
    }


    @Transactional
    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.email())) {
            throw new RegistrationException("Can`t register user. Account with email "
                    + requestDto.email() + " is already exists");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(userRole));
        // CONVERSATION CREATION
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find user with id: " + id)
        );
        return userMapper.toDto(user);
    }


}
