package dev.educationSystem.dto.mapper;

import dev.educationSystem.dto.request.ScheduleForTeacherResponseDto;
import dev.educationSystem.entity.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleForTeacherMapper {
    ScheduleForTeacherResponseDto toDto(Schedule schedule);

    Schedule toEntity(ScheduleForTeacherResponseDto requestScheduleForTeacherDto);
}
