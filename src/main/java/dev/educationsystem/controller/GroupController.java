package dev.educationsystem.controller;

import dev.educationsystem.dto.GroupDto;
import dev.educationsystem.dto.request.GroupResponseDto;
import dev.educationsystem.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @Operation(
            summary = "Получение страницы с группами",
            description = "Делает Get запрос c параметром Pageable"
    )
    @GetMapping()
    public Page<GroupResponseDto> getAllGroups(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        return groupService.findAllGroups(pageable);
    }

    @Operation(
            summary = "Получние страницы по id",
            description = "Get запрос с id параметром"
    )
    @GetMapping("/{id}")
    public GroupResponseDto getGroup(@PathVariable Long id) {
        return groupService.findGroup(id);
    }

    @Operation(
            summary = "Создание группы",
            description = "Post запрос с телом запроса"
    )
    @PostMapping()
    public GroupResponseDto createGroup(@Validated @RequestBody GroupDto groupDto) {
        return groupService.createGroup(groupDto);
    }

    @Operation(
            summary = "Обновление группы",
            description = "Put запрос с телом запроса и  Id параметром"
    )
    @PutMapping("/{id}")
    public GroupResponseDto updateGroup(@Validated @RequestBody GroupDto groupDto, @PathVariable Long id) {
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
    public GroupResponseDto addStudent(@PathVariable Long id, @RequestParam Long studentId) {
        return groupService.addStudent(id, studentId);
    }

    @Operation(
            summary = "Удаление студента из группы",
            description = "Put запрос c id параметром"
    )
    @PutMapping("/deleteStudent/{groupId}")
    public GroupResponseDto deleteStudent(@PathVariable Long groupId, @RequestParam Long studentId) {
        return groupService.deleteStudent(groupId, studentId);
    }

}
