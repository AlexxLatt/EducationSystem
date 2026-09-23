package dev.educationSystem.controller;


import dev.educationSystem.dto.CourseDto;
import dev.educationSystem.dto.request.CourseResponseDto;
import dev.educationSystem.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @Operation(
            summary = "Получение страницы с курсам",
            description = "Делает Get запрос c параметром Pageable"
    )
    @GetMapping()
    public Page<CourseResponseDto> getAllCourses(Pageable pageable) {
        return courseService.findAllCourses(pageable);
    }

    @Operation(
            summary ="Получение курса по Id",
            description = "Get запрос с id параметром"
    )
    @GetMapping("/{id}")
    public CourseResponseDto getCourse(@PathVariable Long id) {
        return courseService.findCourse(id);
    }

    @Operation(
            summary = "Создание курса",
            description = "Post запрос с телом"
    )
    @PostMapping()
    public CourseResponseDto createCourse(@Validated @RequestBody CourseDto courseDto) {
        return courseService.createCourse(courseDto);
    }

    @Operation(
            summary = "Удаление запроса по id",
            description = "Delete запрос с параметром id"
    )
    @DeleteMapping("/{id}")
    public void deleteCourse(Long id) {
        courseService.deleteCourse(id);
    }

    @Operation(
            summary = "Обновление курса",
            description = "Put запрос с параметрами: тело запроса и id"
    )
    @PutMapping("/{id}")
    public CourseResponseDto updateCourse(@Validated @RequestBody CourseDto courseDto, @PathVariable Long id) {
        return courseService.changeCourse(courseDto, id);
    }
}
