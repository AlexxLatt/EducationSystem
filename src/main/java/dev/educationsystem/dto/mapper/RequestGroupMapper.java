package dev.educationsystem.dto.mapper;


import dev.educationsystem.dto.request.GroupResponseDto;
import dev.educationsystem.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestGroupMapper {
    GroupResponseDto toDto(Group group);

    Group toEntity(GroupResponseDto group);
}
