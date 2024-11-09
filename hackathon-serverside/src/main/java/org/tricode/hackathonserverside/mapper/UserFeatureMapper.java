package org.tricode.hackathonserverside.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.tricode.hackathonserverside.config.MapperConfig;
import org.tricode.hackathonserverside.dto.feature.UserFeatureAddRequestDto;
import org.tricode.hackathonserverside.dto.feature.UserFeatureEditRequestDto;
import org.tricode.hackathonserverside.dto.feature.UserFeatureResponseDto;
import org.tricode.hackathonserverside.model.UserFeature;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface UserFeatureMapper {
    UserFeatureResponseDto toDto (UserFeature userFeature);

    List<UserFeatureResponseDto> toDtoList (List<UserFeature> userFeatureList);

    UserFeature toModel(UserFeatureAddRequestDto requestDto);

    void updateFeatureFromRequestDto(UserFeatureEditRequestDto requestDto,
                                     @MappingTarget UserFeature userFeature);
}
