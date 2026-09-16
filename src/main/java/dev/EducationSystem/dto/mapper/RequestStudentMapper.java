package dev.EducationSystem.dto.mapper;


import dev.EducationSystem.dto.request.RequestStudentDto;
import dev.EducationSystem.entity.Student;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestStudentMapper {

    RequestStudentDto toDto(Student student);

    Student toEntity(RequestStudentDto student);
}
