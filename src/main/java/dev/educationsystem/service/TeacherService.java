package dev.educationsystem.service;


import dev.educationsystem.dto.mapper.RequestTeacherMapper;
import dev.educationsystem.dto.TeacherDto;
import dev.educationsystem.dto.request.TeacherResponseDto;
import dev.educationsystem.entity.Teacher;
import dev.educationsystem.exсeption.BadRequestException;
import dev.educationsystem.exсeption.NotFoundException;
import dev.educationsystem.repository.ScheduleRepository;
import dev.educationsystem.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final ScheduleRepository scheduleRepository;
    private final RequestTeacherMapper requestTeacherMapper;
    private static final String NOT_FOUND_TEACHER_MASSAGE = "Учитель с таким id не найден";

    public Page<TeacherResponseDto> findAllTeachers(Pageable pageable){

        return teacherRepository.findAll(pageable).map(requestTeacherMapper::toDto);
    }


    public TeacherResponseDto getTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_TEACHER_MASSAGE));
        return requestTeacherMapper.toDto(teacher);
    }

    public TeacherResponseDto changeTeacher(TeacherDto teacherDto, Long id) {

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_TEACHER_MASSAGE));

        teacher.setFirstName(teacherDto.firstName());
        teacher.setSecondName(teacherDto.secondName());
        teacherRepository.save(teacher);

        return requestTeacherMapper.toDto(teacher);

    }

    public void deleteTeacher(Long id){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_TEACHER_MASSAGE));
        if (!scheduleRepository.findByTeacherId(id).isEmpty()) {
            throw new BadRequestException(
                    "Нельзя удалить преподавателя: он есть в расписании"
            );
        }
        teacherRepository.deleteById(id);
    }

    public TeacherResponseDto createTeacher(TeacherDto teacherDto){

        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDto.firstName());
        teacher.setSecondName(teacherDto.secondName());
        teacherRepository.save(teacher);

        return requestTeacherMapper.toDto(teacher);
    }






}
