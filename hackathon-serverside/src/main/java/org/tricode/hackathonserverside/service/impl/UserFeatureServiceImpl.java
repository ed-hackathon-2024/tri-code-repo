package org.tricode.hackathonserverside.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tricode.hackathonserverside.dto.feature.UserFeatureAddRequestDto;
import org.tricode.hackathonserverside.dto.feature.UserFeatureEditRequestDto;
import org.tricode.hackathonserverside.dto.feature.UserFeatureResponseDto;
import org.tricode.hackathonserverside.mapper.UserFeatureMapper;
import org.tricode.hackathonserverside.model.User;
import org.tricode.hackathonserverside.model.UserFeature;
import org.tricode.hackathonserverside.repository.UserFeaturesRepository;
import org.tricode.hackathonserverside.repository.UserRepository;
import org.tricode.hackathonserverside.service.UserFeatureService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFeatureServiceImpl implements UserFeatureService {
    private final UserRepository userRepository;
    private final UserFeatureMapper userFeatureMapper;
    private final UserFeaturesRepository userFeaturesRepository;

    @Override
    public UserFeatureResponseDto addFeature(Long userId, UserFeatureAddRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found")
        );
        UserFeature feature = userFeatureMapper.toModel(requestDto);
        feature.setUser(user);
        userFeaturesRepository.save(feature);
        return userFeatureMapper.toDto(feature);
    }

    @Override
    public List<UserFeatureResponseDto> addFeatureList(
            Long userId, List<UserFeatureAddRequestDto> requestDtoList) {
        List<UserFeatureResponseDto> responseDtoList = new ArrayList<>();
        for (UserFeatureAddRequestDto requestDto : requestDtoList) {
            responseDtoList.add(addFeature(userId, requestDto));

        }
        return responseDtoList;
    }

    @Override
    public List<UserFeatureResponseDto> getUsersFeatures(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found")
        );
        return userFeatureMapper.toDtoList(user.getUserFeatures());
    }

    @Override
    public void deleteFeatureByQuestion(Long userId, String question) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("User with id " + userId + " not found")
                );

        boolean featureRemoved =
                user.getUserFeatures().removeIf(feature -> feature.getQuestion().equals(question));

        if (!featureRemoved) {
            throw new EntityNotFoundException("Feature with question " + question + " not found");
        }

        userRepository.save(user);
    }

    @Override
    public UserFeatureResponseDto updateFeature(Long userId, UserFeatureEditRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found")
        );
        UserFeature feature = user.getUserFeatures().stream()
                .filter(userFeature -> userFeature.getQuestion().equals(requestDto.question()))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException("User with question " + requestDto.question() + " not found")
                );
        userFeatureMapper.updateFeatureFromRequestDto(requestDto, feature);
        userFeaturesRepository.save(feature);
        return userFeatureMapper.toDto(feature);
    }
}
