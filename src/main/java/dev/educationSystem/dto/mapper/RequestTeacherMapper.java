package dev.educationSystem.dto.mapper;

import dev.educationSystem.dto.request.TeacherResponseDto;
import dev.educationSystem.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestTeacherMapper {

    TeacherResponseDto toDto(Teacher teacher);

    Teacher toEntity(TeacherResponseDto teacher);
}
