package dev.EducationSystem.controller;

import dev.EducationSystem.dto.ScheduleDto;
import dev.EducationSystem.dto.request.RequestScheduleDto;
import dev.EducationSystem.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(
            summary = "Получение страницы с расписаниями",
            description = "Делает Get запрос c параметром Pageable"
    )
    @GetMapping()
    public Page<RequestScheduleDto> findAllSchedule(Pageable pageable) {
        return scheduleService.getAllSchedule(pageable);
    }

    @Operation(
            summary = "Получение расписания",
            description = "Get запрос с id параметром"
    )
    @GetMapping("/{id}")
    public RequestScheduleDto findSchedule(@PathVariable Long id) {
        return scheduleService.getSchedule(id);
    }

    @Operation(
            summary = "Создание расписания",
            description = "Post запрос с телом запроса"
    )
    @PostMapping()
    public RequestScheduleDto createSchedule(@Validated @RequestBody ScheduleDto scheduleDto) {
        return scheduleService.createSchedule(scheduleDto);
    }

    @Operation(
            summary = "Обновление расписание",
            description = "Put запрос с телом запроса и id параметром"
    )
    @PutMapping("/{id}")
    public RequestScheduleDto updateSchedule(@Validated @RequestBody ScheduleDto scheduleDto, @PathVariable Long id) {
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
