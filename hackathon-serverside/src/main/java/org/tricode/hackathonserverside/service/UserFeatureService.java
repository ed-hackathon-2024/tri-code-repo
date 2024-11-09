package org.tricode.hackathonserverside.service;

import org.tricode.hackathonserverside.dto.feature.UserFeatureAddRequestDto;
import org.tricode.hackathonserverside.dto.feature.UserFeatureEditRequestDto;
import org.tricode.hackathonserverside.dto.feature.UserFeatureResponseDto;

import java.util.List;

public interface UserFeatureService {
    UserFeatureResponseDto addFeature(Long userId, UserFeatureAddRequestDto requestDto);

    List<UserFeatureResponseDto> addFeatureList(Long userId,
                                                List<UserFeatureAddRequestDto> requestDtoList);

    List<UserFeatureResponseDto> getUsersFeatures(Long userId);

    void deleteFeatureByQuestion(Long userId, String question);

    UserFeatureResponseDto changeAnswer(Long userId, UserFeatureEditRequestDto requestDto);
}
