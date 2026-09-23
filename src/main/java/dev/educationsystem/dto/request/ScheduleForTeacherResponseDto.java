package dev.educationsystem.dto.request;

import java.time.LocalDateTime;

public record ScheduleForTeacherResponseDto(
        LocalDateTime classStartDate,
        LocalDateTime classEndDate


){
}
