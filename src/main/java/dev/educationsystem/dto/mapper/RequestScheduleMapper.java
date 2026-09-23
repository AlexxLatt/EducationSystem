package dev.educationsystem.dto.mapper;


import dev.educationsystem.dto.request.ScheduleResponseDto;
import dev.educationsystem.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RequestScheduleMapper {

    @Mapping(source = "group.id", target = "groupId")
    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "course.id", target = "courseId")
    ScheduleResponseDto toDto(Schedule schedule);

    Schedule toEntity(ScheduleResponseDto schedule);
}
