package dev.EducationSystem.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RequestScheduleDto(

        @NotNull(message = "Значение не должен быть null")
        Long groupId,
        @NotNull(message = "Значение не должен быть null")
        Long teacherId,
        @NotNull(message = "Значение не должен быть null")
        Long courseId,

        @NotNull(message = "Значение не должен быть null")
        LocalDateTime classStartDate,
        @NotNull(message = "Значение не должен быть null")
        LocalDateTime classEndDate

) {
}
