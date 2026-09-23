package dev.educationSystem.controller;

import dev.educationSystem.dto.ScheduleDto;
import dev.educationSystem.dto.request.ScheduleForTeacherResponseDto;
import dev.educationSystem.dto.request.ScheduleResponseDto;
import dev.educationSystem.dto.request.ScheduleFilter;
import dev.educationSystem.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(
            summary = "Получение страницы с расписаниями",
            description = "Делает Get запрос c параметром Pageable"
    )
    @GetMapping()
    public Page<ScheduleResponseDto> findAllSchedules(Pageable pageable) {
        return scheduleService.getAllSchedules(pageable);
    }

    @Operation(
            summary = "Получение расписания",
            description = "Get запрос с id параметром"
    )
    @GetMapping("/{id}")
    public ScheduleResponseDto findSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    @Operation(
            summary = "Фильтр по расписанию",
            description = "Get запрос параметром с ModelAttribute параметрами"
    )
    @GetMapping("/filter")
    public Page<ScheduleResponseDto> findCoursesScheduleForGroup(@ModelAttribute ScheduleFilter filter, Pageable pageable){
        return scheduleService.findScheduleCursesForGroup(filter, pageable);
    }

    @Operation(
            summary = "Получение расписания для учителя",
            description = "Get запрос с id параметром"
    )
    @GetMapping("/teacher/{id}")
    public List<ScheduleForTeacherResponseDto> findScheduleForTeacher(@PathVariable Long id){
       return  scheduleService.findScheduleForTeacher(id);
    }

    @Operation(
            summary = "Создание расписания",
            description = "Post запрос с телом запроса"
    )
    @PostMapping()
    public ScheduleResponseDto createSchedule(@Validated @RequestBody ScheduleDto scheduleDto) {
        return scheduleService.createSchedule(scheduleDto);
    }

    @Operation(
            summary = "Обновление расписание",
            description = "Put запрос с телом запроса и id параметром"
    )
    @PutMapping("/{id}")
    public ScheduleResponseDto updateSchedule(@Validated @RequestBody ScheduleDto scheduleDto, @PathVariable Long id) {
        return scheduleService.changeSchedule(scheduleDto, id);
    }

    @Operation(
            summary = "Удаление запроса",
            description = "DELETE запрос с id параметром"
    )
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }

}
