package dev.EducationSystem.dto.mapper;

import dev.EducationSystem.dto.request.RequestScheduleForTeacherDto;
import dev.EducationSystem.entity.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleForTeacherMapper {
    RequestScheduleForTeacherDto toDto(Schedule schedule);

    Schedule toEntity(RequestScheduleForTeacherDto requestScheduleForTeacherDto);
}
