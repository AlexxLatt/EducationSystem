package dev.EducationSystem.dto.request;

import java.time.LocalDateTime;

public record RequestScheduleForTeacherDto(
        LocalDateTime classStartDate,
        LocalDateTime classEndDate


){
}
