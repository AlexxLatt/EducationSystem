package dev.educationSystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Информация о студенте")
public record StudentDto(



        @Schema(description = "Имя", required = true, example = "Алексей")
        @NotBlank(message = "Имя не должно быть пустым")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        String firstName,

        @Schema(description = "Фамилия", required = true, example = "Смирнов") @NotBlank(message = "Фамилия не должна быть пустой")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        String secondName

) {
}
