package dev.educationsystem.dto.mapper;


import dev.educationsystem.dto.request.CourseResponseDto;
import dev.educationsystem.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestCourseMapper {
    CourseResponseDto toDto(Course course);

    Course toEntity(CourseResponseDto course);
}
