package dev.EducationSystem.controller;

import dev.EducationSystem.dto.GroupDto;
import dev.EducationSystem.dto.request.RequestGroupDto;
import dev.EducationSystem.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @Operation(
            summary = "Получение страницы с группами",
            description = "Делает Get запрос c параметром Pageable"
    )
    @GetMapping()
    public Page<RequestGroupDto> getAllGroup(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        return groupService.findAllGroup(pageable);
    }

    @Operation(
            summary = "Получние страницы по id",
            description = "Get запрос с id параметром"
    )
    @GetMapping("/{id}")
    public RequestGroupDto getGroup(@PathVariable Long id) {
        return groupService.findGroup(id);
    }

    @Operation(
            summary = "Создание группы",
            description = "Post запрос с телом запроса"
    )
    @PostMapping()
    public RequestGroupDto createGroup(@Validated @RequestBody GroupDto groupDto) {
        return groupService.createGroup(groupDto);
    }

    @Operation(
            summary = "Обновление группы",
            description = "Put запрос с телом запроса и  Id параметром"
    )
    @PutMapping("/{id}")
    public RequestGroupDto updateGroup(@Validated @RequestBody GroupDto groupDto, @PathVariable Long id) {
        return groupService.changeGroup(groupDto, id);
    }

    @Operation(
            summary = "Удаление группы",
            description = "DELETE запрос с id параметром"
    )
    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
    }

    @Operation(
            summary = "Добавление студена в группу",
            description = "Put запрос c id параметром"
    )
    @PutMapping("/addStudent/{id}")
    public RequestGroupDto addStudent(@PathVariable Long id, @RequestParam Long studentId) {
        return groupService.addStudent(id, studentId);
    }

    @Operation(
            summary = "Удаление студента из группы",
            description = "Put запрос c id параметром"
    )
    @PutMapping("/deleteStudent/{groupId}")
    public RequestGroupDto deleteStudent(@PathVariable Long groupId, @RequestParam Long studentId) {
        return groupService.deleteStudent(groupId, studentId);
    }

}
