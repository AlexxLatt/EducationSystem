package dev.EducationSystem.service;


import dev.EducationSystem.dto.mapper.RequestScheduleMapper;
import dev.EducationSystem.dto.ScheduleDto;
import dev.EducationSystem.dto.request.RequestScheduleDto;
import dev.EducationSystem.entity.Course;
import dev.EducationSystem.entity.Group;
import dev.EducationSystem.entity.Schedule;
import dev.EducationSystem.entity.Teacher;
import dev.EducationSystem.exсeption.BadRequestException;
import dev.EducationSystem.exсeption.NotFoundException;
import dev.EducationSystem.repository.CourseRepository;
import dev.EducationSystem.repository.GroupRepository;
import dev.EducationSystem.repository.ScheduleRepository;
import dev.EducationSystem.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final RequestScheduleMapper requestScheduleMapper;


    public Page<RequestScheduleDto> getAllSchedule(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(requestScheduleMapper::toDto);
    }

    public RequestScheduleDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Расписания с таким Id нет"));
        return requestScheduleMapper.toDto(schedule);
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Расписания с таким Id нет"));
        scheduleRepository.delete(schedule);
    }

    public RequestScheduleDto createSchedule(ScheduleDto scheduleDto) {

        Long groupId = scheduleDto.groupId();
        Long teacherId = scheduleDto.teacherId();
        Long courseId = scheduleDto.courseId();

        if (scheduleRepository.findByTeacherId(teacherId).isPresent()) {
            throw new BadRequestException("Учитель уже занят");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Курс не найден"));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Преподаватель не найден"));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Группа не найдена"));

        Schedule schedule = new Schedule();
        schedule.setCourse(course);
        schedule.setTeacher(teacher);
        schedule.setGroup(group);
        schedule.setClassStartDate(scheduleDto.classStartDate());
        schedule.setClassEndDate(scheduleDto.classEndDate());

        scheduleRepository.save(schedule);

        return requestScheduleMapper.toDto(schedule);


    }

    public RequestScheduleDto changeSchedule( ScheduleDto scheduleDto,Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Расписания с таким Id нет"));

        Long groupIdDto = scheduleDto.groupId();
        Long teacherIdDto = scheduleDto.teacherId();
        LocalDateTime classStartDateDto = scheduleDto.classStartDate();
        LocalDateTime classEndDateDto = scheduleDto.classEndDate();


        Long groupIdSchedule = schedule.getGroup().getId();
        Long teacherIdSchedule = schedule.getTeacher().getId();

        if (!teacherIdDto.equals(teacherIdSchedule)) {
            Teacher teacher = teacherRepository.findById(teacherIdDto)
                    .orElseThrow(() -> new NotFoundException("Преподаватель не найден"));

            if (scheduleRepository.findByTeacherId(teacherIdDto).isPresent()) {
                throw new BadRequestException("Учитель уже занят");
            }
            schedule.setTeacher(teacher);
        }


        if (!groupIdDto.equals(groupIdSchedule)) {
            Group group = groupRepository.findById(groupIdDto)
                    .orElseThrow(() -> new NotFoundException("Группа не найдена"));

            schedule.setGroup(group);
        }

        schedule.setClassStartDate(classStartDateDto);
        schedule.setClassEndDate(classEndDateDto);

        scheduleRepository.save(schedule);

        return requestScheduleMapper.toDto(schedule);

    }


}
