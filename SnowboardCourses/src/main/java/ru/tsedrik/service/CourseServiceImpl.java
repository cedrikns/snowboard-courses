package ru.tsedrik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.controller.dto.*;
import ru.tsedrik.domain.*;
import ru.tsedrik.exception.CourseNotFoundException;
import ru.tsedrik.exception.PersonNotFoundException;
import ru.tsedrik.repository.CourseRepository;
import ru.tsedrik.repository.PersonRepository;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Реализация интерфейса CourseService
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService{

    /**
     * Объект для управления персистентным состоянием объектов типа Course
     */
    private CourseRepository courseRepository;

    /**
     * Объект для управления персистентным состоянием объектов типа Person
     */
    private PersonRepository personRepository;

    /**
     * Максимальное количество групп на курсе
     */
    @Value("${course.maxGroupCount}")
    private String maxGroupCount;

    /**
     * Максимальное количество участников в одной группе
     */
    @Value("${group.maxPersonPerGroup}")
    private String maxPersonPerGroup;

    /**
     * Шаблон сообщения об ошибке для исключения CourseNotFoundException
     */
    @Value("${exception.courseNotFound}")
    private String courseNotFoundExMsg;

    /**
     * Шаблон сообщения об ошибке для исключения PersonNotFoundException
     */
    @Value("${exception.personNotFound}")
    private String personNotFoundExMsg;

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class.getName());

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public CourseDto addCourse(CourseDto courseDto) {

        Course course = new Course(
                System.currentTimeMillis(), courseDto.getCourseType(), courseDto.getLocation(),
                courseDto.getStartTime(), courseDto.getEndTime(),
                courseDto.getGroupCount()
        );

        for (int i = 0; i < course.getGroupCount(); i++){
            Group group = new Group(System.currentTimeMillis() + i + 1, course.getId(), Integer.parseInt(maxPersonPerGroup));
            course.getGroups().add(group);
        }

        course = courseRepository.save(course);

        courseDto.setId(course.getId());
        courseDto.setGroups(course.getGroups());

        return courseDto;
    }

    @Override
    public boolean deleteCourse(Course course) {
        courseRepository.delete(course);
        return true;
    }

    @Override
    public boolean deleteCourseById(Long id) {
        courseRepository.deleteById(id);
        return true;
    }

    @Override
    public CourseDto getCourseById(Long id) {
        CourseDto courseDto = courseRepository.findById(id)
                .map(course -> new CourseDto(
                        course.getId(), course.getCourseType().toString(), course.getLocation(),
                        course.getBeginDate(), course.getEndDate(),
                        course.getGroupCount(), course.getGroups()))
                .orElseThrow(() -> new CourseNotFoundException(courseNotFoundExMsg + id));

        return courseDto;
    }

    public CourseDto enroll(Long courseId, Long personId){
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseNotFoundExMsg + courseId));

        if (!course.isAvailableGroupExist()){
            throw new IllegalArgumentException("There is no available places on this course");
        }

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException(personNotFoundExMsg + "with id = " + personId));

        Group group = course.getAvailableGroup();
        Set<Person> students = group.getStudents();
        if (students.add(person)) {
            group.setStudents(students);
            group.setAvailableNumberOfPlaces(group.getTotalNumberOfPlaces() - students.size());
        } else {
            throw new IllegalArgumentException("Участник " + person.getId() + " не был добавлен на курс.");
        }

        courseRepository.save(course);

        CourseDto courseDto = new CourseDto(
                course.getId(), course.getCourseType().toString(), course.getLocation(),
                course.getBeginDate(), course.getEndDate(),
                course.getGroupCount(), course.getGroups()
        );

        return courseDto;
    }

    @Override
    public PageDto<CourseDto> getCourses(CourseSearchDto courseSearchDto, Pageable pageable) {
        Page<Course> page = courseRepository.findAll(getSpecification(courseSearchDto), pageable);

        List<CourseDto> courses = page
                .map(course ->
                        new CourseDto(
                                course.getId(), course.getCourseType().toString(), course.getLocation(),
                                course.getBeginDate(), course.getEndDate(),
                                course.getGroupCount(), course.getGroups())
                )
                .toList();

        return new PageDto<>(courses, page.getTotalElements());
    }

    private Specification<Course> getSpecification(CourseSearchDto courseSearchDto) {
        return (root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (courseSearchDto.getCourseType() != null) {
                predicates.add(root.get("courseType").in(CourseType.valueOf(courseSearchDto.getCourseType().toUpperCase())));
            }

            if (courseSearchDto.getStartTime() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("beginDate"), courseSearchDto.getStartTime()));
            }

            if (courseSearchDto.getEndTime() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("endDate"), courseSearchDto.getEndTime()));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
