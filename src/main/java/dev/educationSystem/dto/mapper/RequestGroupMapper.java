package dev.educationSystem.dto.mapper;


import dev.educationSystem.dto.request.GroupResponseDto;
import dev.educationSystem.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestGroupMapper {
    GroupResponseDto toDto(Group group);

    Group toEntity(GroupResponseDto group);
}
