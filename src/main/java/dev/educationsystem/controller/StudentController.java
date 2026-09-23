package dev.educationsystem.controller;

import dev.educationsystem.dto.StudentDto;
import dev.educationsystem.dto.request.StudentResponseDto;
import dev.educationsystem.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @Operation(
            summary = "Получение страницы со студентами",
            description = "Делает Get запрос c параметром Pageable"
    )
    @GetMapping()
    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
        return studentService.findAllStudents(pageable);
    }

    @Operation(
            summary = "Получние студента",
            description = "Get запрос с id параметром"
    )
    @GetMapping("/{id}")
    public StudentResponseDto getStudent(@PathVariable Long id) {
        return studentService.findStudent(id);
    }

    @Operation(
            summary = "Создание студента",
            description = "Post запрос с телом запроса"
    )
    @PostMapping()
    public StudentResponseDto createStudent(@Validated @RequestBody StudentDto studentDto) {
        return studentService.createStudent(studentDto);
    }

    @Operation(
            summary = "Обновление студнета",
            description = "Put запрос с телом запрос и id параметром"
    )
    @PutMapping("/{id}")
    public StudentResponseDto updateStudent(@Validated @RequestBody StudentDto studentDto, @PathVariable Long id) {
        return studentService.changeStudent(studentDto, id);
    }

    @Operation(
            summary = "Удаление студента",
            description = "DELETE запрос с id параметром"
    )
    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @Operation(
            summary = "Присоединение студента к группе",
            description = "Put запрос с 2 id параметрами"
    )
    @PutMapping("/joinGroup/{id}")
    public StudentResponseDto joinGroup(@PathVariable Long id, @RequestParam Long groupId) {
        return studentService.joinGroup(groupId,id);
    }

    @Operation(
            summary = "Исключение студента из группу",
            description = "Put запрос с 2 id параметрами"
    )
    @PutMapping("/leaveGroup/{id}")
    public StudentResponseDto leaveGroup(@PathVariable Long id, @RequestParam Long groupId) {
        return studentService.leaveGroup(groupId,id);
    }
}
