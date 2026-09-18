package dev.EducationSystem.service;


import dev.EducationSystem.dto.mapper.RequestStudentMapper;
import dev.EducationSystem.dto.StudentDto;
import dev.EducationSystem.dto.request.RequestStudentDto;
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
public class StudentService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final RequestStudentMapper requestStudentMapper;
    private static final String NOT_FOUND_MASSAGE = "Студента с таким Id нет";
    private static final String NOT_FOUND_GROUP_MASSAGE = "Группа с таким id не найдена";

    public Page<RequestStudentDto> findAllStudent(Pageable pageable) {

        return studentRepository.findAll(pageable).map(requestStudentMapper::toDto);

    }

    public RequestStudentDto findStudent(Long id) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MASSAGE));

        return requestStudentMapper.toDto(student);

    }

    public RequestStudentDto changeStudent(StudentDto studentDto, Long id) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MASSAGE));

        student.setFirstName(studentDto.firstName());

        student.setSecondName(studentDto.secondName());

        return requestStudentMapper.toDto(student);

    }

    public void  deleteStudent(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MASSAGE));
        studentRepository.delete(student);
    }

    public RequestStudentDto createStudent(StudentDto studentDto){
        Student student = new Student();

        student.setFirstName(studentDto.firstName());
        student.setSecondName(studentDto.secondName());

        studentRepository.save(student);

        return requestStudentMapper.toDto(student);
    }


    public RequestStudentDto leaveGroup(Long groupId ,Long studentId){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException(NOT_FOUND_MASSAGE));

        student.getGroups().remove(group);
        studentRepository.save(student);

        return requestStudentMapper.toDto(student);
    }

    public RequestStudentDto joinGroup(Long groupId ,Long studentId){

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException(NOT_FOUND_MASSAGE));

        if(group.getStudents().contains(student)){
            throw new BadRequestException("Студент уже находится в этой группе");
        }else{
            student.getGroups().add(group);
            studentRepository.save(student);
        }

        return requestStudentMapper.toDto(student);
    }
}
