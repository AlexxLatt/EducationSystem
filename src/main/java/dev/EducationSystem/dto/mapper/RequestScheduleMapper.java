package dev.EducationSystem.dto.mapper;


import dev.EducationSystem.dto.request.RequestScheduleDto;
import dev.EducationSystem.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequestScheduleMapper {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "course.id", target = "courseId")
    RequestScheduleDto toDto(Schedule schedule);

    Schedule toEntity(RequestScheduleDto schedule);
}
