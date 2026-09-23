package dev.educationsystem.dto.mapper;

import dev.educationsystem.dto.request.ScheduleForTeacherResponseDto;
import dev.educationsystem.entity.Schedule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleForTeacherMapper {
    ScheduleForTeacherResponseDto toDto(Schedule schedule);

    Schedule toEntity(ScheduleForTeacherResponseDto requestScheduleForTeacherDto);
}
