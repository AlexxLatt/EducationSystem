package dev.educationsystem.dto.mapper;


import dev.educationsystem.dto.request.StudentResponseDto;
import dev.educationsystem.entity.Student;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestStudentMapper {

    StudentResponseDto toDto(Student student);

    Student toEntity(StudentResponseDto student);
}
