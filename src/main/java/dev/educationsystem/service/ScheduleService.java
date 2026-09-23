package dev.educationsystem.service;


import dev.educationsystem.dto.mapper.RequestScheduleMapper;
import dev.educationsystem.dto.ScheduleDto;
import dev.educationsystem.dto.mapper.ScheduleForTeacherMapper;
import dev.educationsystem.dto.request.ScheduleResponseDto;
import dev.educationsystem.dto.request.ScheduleForTeacherResponseDto;
import dev.educationsystem.entity.Course;
import dev.educationsystem.entity.Group;
import dev.educationsystem.entity.Schedule;
import dev.educationsystem.entity.Teacher;
import dev.educationsystem.exсeption.BadRequestException;
import dev.educationsystem.exсeption.NotFoundException;
import dev.educationsystem.dto.request.ScheduleFilter;
import dev.educationsystem.repository.CourseRepository;
import dev.educationsystem.repository.GroupRepository;
import dev.educationsystem.repository.ScheduleRepository;
import dev.educationsystem.repository.TeacherRepository;
import dev.educationsystem.specifications.ScheduleSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final RequestScheduleMapper requestScheduleMapper;
    private final ScheduleForTeacherMapper scheduleForTeacherMapper;


    private static final String NOT_FOUND_GROUP_MASSAGE = "Группа с таким id не найдена";
    private static final String NOT_FOUND_TEACHER_MASSAGE = "Учитель с таким id не найден";
    private static final String BAD_REQUEST_TEACHER_MASSAGE = "Учитель уже занят";

    public Page<ScheduleResponseDto> getAllSchedules(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(requestScheduleMapper::toDto);
    }

    public ScheduleResponseDto getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));
        return requestScheduleMapper.toDto(schedule);
    }

    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));
        scheduleRepository.delete(schedule);
    }

    public ScheduleResponseDto createSchedule(ScheduleDto scheduleDto) {

        Long groupId = scheduleDto.groupId();
        Long teacherId = scheduleDto.teacherId();
        Long courseId = scheduleDto.courseId();

        if (!scheduleRepository.findByTeacherId(teacherId).isEmpty()) {
            throw new BadRequestException(BAD_REQUEST_TEACHER_MASSAGE);
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Курс не найден"));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_TEACHER_MASSAGE));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));

        Schedule schedule = new Schedule();
        schedule.setCourse(course);
        schedule.setTeacher(teacher);
        schedule.setGroup(group);
        schedule.setClassStartDate(scheduleDto.classStartDate());
        schedule.setClassEndDate(scheduleDto.classEndDate());

        scheduleRepository.save(schedule);

        return requestScheduleMapper.toDto(schedule);


    }

    public Page<ScheduleResponseDto> findScheduleCursesForGroup(ScheduleFilter scheduleFilter, Pageable pageable) {
        Specification<Schedule> spec = ScheduleSpecifications.findScheduleCursesForGroup(scheduleFilter);

        return scheduleRepository.findAll(spec, pageable)
                .map(requestScheduleMapper::toDto);
    }

    public List<ScheduleForTeacherResponseDto> findScheduleForTeacher(Long id) {
        List<Schedule> schedule = scheduleRepository.findByTeacherId(id);
        if (schedule.isEmpty()) {
            throw new BadRequestException(NOT_FOUND_TEACHER_MASSAGE);
        }
        return schedule.stream().map(scheduleForTeacherMapper::toDto).toList();
    }

    public ScheduleResponseDto changeSchedule(ScheduleDto scheduleDto, Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));

        Long groupIdDto = scheduleDto.groupId();
        Long teacherIdDto = scheduleDto.teacherId();
        LocalDateTime classStartDateDto = scheduleDto.classStartDate();
        LocalDateTime classEndDateDto = scheduleDto.classEndDate();


        Long groupIdSchedule = schedule.getGroup().getId();
        Long teacherIdSchedule = schedule.getTeacher().getId();

        if (!teacherIdDto.equals(teacherIdSchedule)) {
            Teacher teacher = teacherRepository.findById(teacherIdDto)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_TEACHER_MASSAGE));

            if (!scheduleRepository.findByTeacherId(teacherIdDto).isEmpty()) {
                throw new BadRequestException(BAD_REQUEST_TEACHER_MASSAGE);
            }
            schedule.setTeacher(teacher);
        }


        if (!groupIdDto.equals(groupIdSchedule)) {
            Group group = groupRepository.findById(groupIdDto)
                    .orElseThrow(() -> new NotFoundException(NOT_FOUND_GROUP_MASSAGE));

            schedule.setGroup(group);
        }

        schedule.setClassStartDate(classStartDateDto);
        schedule.setClassEndDate(classEndDateDto);

        scheduleRepository.save(schedule);

        return requestScheduleMapper.toDto(schedule);

    }


}
