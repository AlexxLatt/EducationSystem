package dev.educationsystem.dto.mapper;

import dev.educationsystem.dto.request.TeacherResponseDto;
import dev.educationsystem.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestTeacherMapper {

    TeacherResponseDto toDto(Teacher teacher);

    Teacher toEntity(TeacherResponseDto teacher);
}
