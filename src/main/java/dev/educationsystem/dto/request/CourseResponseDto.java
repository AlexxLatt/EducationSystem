package dev.educationsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseResponseDto(

        @NotBlank(message = "Имя не должно быть пустым")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        String name,

        @NotBlank(message = "Описание не должно быть пустым")
        @Size(min = 2, max = 100, message = "Описание должно быть от 2 до 100 символов")
        String description

) {
}