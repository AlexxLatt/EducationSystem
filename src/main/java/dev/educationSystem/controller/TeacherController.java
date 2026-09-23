package dev.educationSystem.controller;

import dev.educationSystem.dto.TeacherDto;

import dev.educationSystem.dto.request.TeacherResponseDto;
import dev.educationSystem.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @Operation(
            summary = "Получение страницы с учителями",
            description = "Делает Get запрос c параметром Pageable"
    )
    @GetMapping()
    public Page<TeacherResponseDto> getAllTeachers(Pageable pageable) {
        return teacherService.findAllTeachers(pageable);
    }

    @Operation(
            summary = "Получение учителя",
            description = "Get запрос с id параметром"
    )
    @GetMapping("/{id}")
    public TeacherResponseDto getTeacher(@PathVariable Long id) {
        return teacherService.getTeacher(id);
    }

    @Operation(
            summary = "Создание учителя",
            description = "Post запрос с телом запроса"
    )
    @PostMapping()
    public TeacherResponseDto createTeacher(@Validated @RequestBody TeacherDto teacherDto) {
        return teacherService.createTeacher(teacherDto);
    }

    @Operation(
            summary = "Обновление учителя",
            description = "Put запрос с телом запроса и id параметром"
    )
    @PutMapping("/{id}")
    public TeacherResponseDto updateTeacher(@Validated @RequestBody TeacherDto teacherDto, @PathVariable Long id) {
        return teacherService.changeTeacher(teacherDto, id);
    }

    @Operation(
            summary = "Удаление учителя",
            description = "DELETE запрос с id параметром"
    )
    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }

}
