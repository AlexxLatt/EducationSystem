package dev.educationSystem.service;


import dev.educationSystem.dto.GroupDto;
import dev.educationSystem.dto.mapper.RequestGroupMapper;
import dev.educationSystem.dto.request.GroupResponseDto;
import dev.educationSystem.entity.Group;
import dev.educationSystem.entity.Student;
import dev.educationSystem.exсeption.BadRequestException;
import dev.educationSystem.exсeption.NotFoundException;
import dev.educationSystem.repository.GroupRepository;
import dev.educationSystem.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final RequestGroupMapper requestGroupMapper;
    private final StudentRepository studentRepository;
    private static final String NOT_FOUND_GROUP_MASSAGE = "Группы с таким Id нет";
    private static final String NOT_FOUND_STUDENT_MASSAGE = "Студента с таким Id нет";
    private static final String BAD_REQUEST_STUDENT_MASSAGE = "Студент не находится в этой группе";


    public Page<GroupResponseDto> findAllGroups(Pageable pageable) {

        return groupRepository.findAll(pageable).map(requestGroupMapper::toDto);
    }

    public GroupResponseDto findGroup(Long id) {

        Group group = groupRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));

        return requestGroupMapper.toDto(group);
    }


    public GroupResponseDto changeGroup(GroupDto groupDto, Long id) {

        Group group = groupRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));
        group.setName(groupDto.name());

        return requestGroupMapper.toDto(group);
    }

    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));

        groupRepository.deleteById(id);
    }

    public GroupResponseDto createGroup(GroupDto groupDto) {
        Group group = new Group();

        group.setName(groupDto.name());
        groupRepository.save(group);
        return requestGroupMapper.toDto(group);
    }


    public GroupResponseDto deleteStudent(Long groupId, Long studentId) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDENT_MASSAGE));

        if (!student.getGroups().contains(group)) {
            throw new BadRequestException(BAD_REQUEST_STUDENT_MASSAGE);
        }

        student.getGroups().remove(group);
        studentRepository.save(student);

        return requestGroupMapper.toDto(group);
    }

    public GroupResponseDto addStudent(Long groupId, Long studentId) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_STUDENT_MASSAGE));

        if (student.getGroups().contains(group)) {
            throw new BadRequestException(BAD_REQUEST_STUDENT_MASSAGE);
        }

        student.getGroups().add(group);
        studentRepository.save(student);

        return requestGroupMapper.toDto(group);
    }



}
