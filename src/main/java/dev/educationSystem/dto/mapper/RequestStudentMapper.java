package dev.educationSystem.dto.mapper;


import dev.educationSystem.dto.request.StudentResponseDto;
import dev.educationSystem.entity.Student;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestStudentMapper {

    StudentResponseDto toDto(Student student);

    Student toEntity(StudentResponseDto student);
}
