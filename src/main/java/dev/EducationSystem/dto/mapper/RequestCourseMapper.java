package dev.EducationSystem.dto.mapper;


import dev.EducationSystem.dto.request.RequestCourseDto;
import dev.EducationSystem.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestCourseMapper {
    RequestCourseDto toDto(Course course);

    Course toEntity(RequestCourseDto course);
}
