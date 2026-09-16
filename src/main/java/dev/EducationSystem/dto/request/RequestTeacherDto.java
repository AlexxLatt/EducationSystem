package dev.EducationSystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestTeacherDto(

        @NotBlank(message = "Имя не должно быть пустым")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        String firstName,

        @NotBlank(message = "Фамилия не должна быть пустой")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        String secondName

) {
}
