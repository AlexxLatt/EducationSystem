package dev.EducationSystem.service;


import dev.EducationSystem.dto.mapper.RequestTeacherMapper;
import dev.EducationSystem.dto.TeacherDto;
import dev.EducationSystem.dto.request.RequestTeacherDto;
import dev.EducationSystem.entity.Teacher;
import dev.EducationSystem.exсeption.BadRequestException;
import dev.EducationSystem.exсeption.NotFoundException;
import dev.EducationSystem.repository.ScheduleRepository;
import dev.EducationSystem.repository.TeacherRepository;
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

    public Page<RequestTeacherDto> findAllTeacher(Pageable pageable){

        return teacherRepository.findAll(pageable).map(requestTeacherMapper::toDto);
    }


    public RequestTeacherDto getTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new NotFoundException("Учителя с таким Id нет"));
        return requestTeacherMapper.toDto(teacher);
    }

    public RequestTeacherDto changeTeacher(TeacherDto teacherDto, Long id) {

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new NotFoundException("Учителя с таким Id нет"));

        teacher.setFirstName(teacherDto.firstName());
        teacher.setSecondName(teacherDto.secondName());
        teacherRepository.save(teacher);

        return requestTeacherMapper.toDto(teacher);

    }

    public void deleteTeacher(Long id){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new NotFoundException("Учителя с таким Id нет"));
        if (scheduleRepository.findByTeacherId(id).isPresent()) {
            throw new BadRequestException(
                    "Нельзя удалить преподавателя: он есть в расписании"
            );
        }
        teacherRepository.deleteById(id);
    }

    public RequestTeacherDto createTeacher(TeacherDto teacherDto){

        Teacher teacher = new Teacher();
        teacher.setFirstName(teacherDto.firstName());
        teacher.setSecondName(teacherDto.secondName());
        teacherRepository.save(teacher);

        return requestTeacherMapper.toDto(teacher);
    }






}
