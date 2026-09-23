package dev.educationsystem.service;


import dev.educationsystem.dto.mapper.RequestStudentMapper;
import dev.educationsystem.dto.StudentDto;
import dev.educationsystem.dto.request.StudentResponseDto;
import dev.educationsystem.entity.Group;
import dev.educationsystem.entity.Student;
import dev.educationsystem.exсeption.BadRequestException;
import dev.educationsystem.exсeption.NotFoundException;
import dev.educationsystem.repository.GroupRepository;
import dev.educationsystem.repository.StudentRepository;
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

    public Page<StudentResponseDto> findAllStudents(Pageable pageable) {

        return studentRepository.findAll(pageable).map(requestStudentMapper::toDto);

    }

    public StudentResponseDto findStudent(Long id) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MASSAGE));

        return requestStudentMapper.toDto(student);

    }

    public StudentResponseDto changeStudent(StudentDto studentDto, Long id) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MASSAGE));

        student.setFirstName(studentDto.firstName());

        student.setSecondName(studentDto.secondName());

        return requestStudentMapper.toDto(student);

    }

    public void  deleteStudent(Long id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_MASSAGE));
        studentRepository.delete(student);
    }

    public StudentResponseDto createStudent(StudentDto studentDto){
        Student student = new Student();

        student.setFirstName(studentDto.firstName());
        student.setSecondName(studentDto.secondName());

        studentRepository.save(student);

        return requestStudentMapper.toDto(student);
    }


    public StudentResponseDto leaveGroup(Long groupId , Long studentId){
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException(NOT_FOUND_MASSAGE));

        student.getGroups().remove(group);
        studentRepository.save(student);

        return requestStudentMapper.toDto(student);
    }

    public StudentResponseDto joinGroup(Long groupId , Long studentId){

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
