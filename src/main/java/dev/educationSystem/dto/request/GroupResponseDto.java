package dev.educationSystem.dto.request;

import dev.educationSystem.dto.StudentDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record GroupResponseDto(

        @NotBlank(message = "Имя не должно быть пустым")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        String name,
        List<StudentDto> students

) {
}
