package dev.EducationSystem;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TestScheduleDto(
        @NotNull(message = "Значение groupId не должен быть null")
        Long groupId,

        @NotNull(message = "Значение teacherId не должен быть null")
        Long teacherId,

        @NotNull(message = "Значение courseId не должен быть null")
        Long courseId,

        @NotNull(message = "Значение classStartDate не должен быть null")
        LocalDateTime classStartDate,

        @NotNull(message = "Значение classEndDate не должен быть null")
        LocalDateTime classEndDate
) {
}
