package dev.EducationSystem.service;


import dev.EducationSystem.dto.GroupDto;
import dev.EducationSystem.dto.mapper.RequestGroupMapper;
import dev.EducationSystem.dto.request.RequestGroupDto;
import dev.EducationSystem.entity.Group;
import dev.EducationSystem.entity.Student;
import dev.EducationSystem.exсeption.BadRequestException;
import dev.EducationSystem.exсeption.NotFoundException;
import dev.EducationSystem.repository.GroupRepository;
import dev.EducationSystem.repository.StudentRepository;
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


    public Page<RequestGroupDto> findAllGroup(Pageable pageable) {

        return groupRepository.findAll(pageable).map(requestGroupMapper::toDto);
    }

    public RequestGroupDto findGroup(Long id) {

        Group group = groupRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));

        return requestGroupMapper.toDto(group);
    }


    public RequestGroupDto changeGroup(GroupDto groupDto, Long id) {

        Group group = groupRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));
        group.setName(groupDto.name());

        return requestGroupMapper.toDto(group);
    }

    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));

        groupRepository.deleteById(id);
    }

    public RequestGroupDto createGroup(GroupDto groupDto) {
        Group group = new Group();

        group.setName(groupDto.name());
        groupRepository.save(group);
        return requestGroupMapper.toDto(group);
    }


    public RequestGroupDto deleteStudent(Long groupId, Long studentId) {

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

    public RequestGroupDto addStudent(Long groupId, Long studentId) {

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
