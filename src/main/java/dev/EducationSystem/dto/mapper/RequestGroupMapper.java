package dev.EducationSystem.dto.mapper;


import dev.EducationSystem.dto.request.RequestGroupDto;
import dev.EducationSystem.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestGroupMapper {
    RequestGroupDto toDto(Group group);

    Group toEntity(RequestGroupDto group);
}
