package dev.educationSystem.dto.mapper;


import dev.educationSystem.dto.request.CourseResponseDto;
import dev.educationSystem.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestCourseMapper {
    CourseResponseDto toDto(Course course);

    Course toEntity(CourseResponseDto course);
}
