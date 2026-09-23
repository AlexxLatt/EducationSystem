package dev.educationSystem.service;

import dev.educationSystem.dto.CourseDto;
import dev.educationSystem.dto.mapper.RequestCourseMapper;
import dev.educationSystem.dto.request.CourseResponseDto;
import dev.educationSystem.entity.Course;
import dev.educationSystem.exсeption.NotFoundException;
import dev.educationSystem.repository.CourseRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final RequestCourseMapper requestCourseMapper;
    private static final String COURSE_NOT_FOUND_MESSAGE = "Курса с таким Id нет";


    public Page<CourseResponseDto> findAllCourses(Pageable pageable) {

        return courseRepository.findAll(pageable).map(requestCourseMapper::toDto);
    }

    public CourseResponseDto findCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND_MESSAGE));
        return requestCourseMapper.toDto(course);
    }

    public CourseResponseDto changeCourse(CourseDto courseDto, Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND_MESSAGE));
        course.setName(courseDto.name());
        course.setDescription(courseDto.description());
        return requestCourseMapper.toDto(course);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new NotFoundException(COURSE_NOT_FOUND_MESSAGE));

        courseRepository.delete(course);
    }

    public CourseResponseDto createCourse(CourseDto courseDto) {
        Course course = new Course();

        course.setName(courseDto.name());
        course.setDescription(courseDto.description());

        courseRepository.save(course);

        return requestCourseMapper.toDto(course);
    }

}
