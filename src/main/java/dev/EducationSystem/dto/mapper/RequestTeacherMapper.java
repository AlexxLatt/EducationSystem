package dev.EducationSystem.dto.mapper;

import dev.EducationSystem.dto.request.RequestTeacherDto;
import dev.EducationSystem.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestTeacherMapper {

    RequestTeacherDto toDto(Teacher teacher);

    Teacher toEntity(RequestTeacherDto teacher);
}
