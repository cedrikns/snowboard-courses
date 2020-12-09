package ru.tsedrik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tsedrik.controller.dto.CourseDto;
import ru.tsedrik.exception.CourseNotFoundException;
import ru.tsedrik.exception.PersonNotFoundException;
import ru.tsedrik.domain.Course;
import ru.tsedrik.domain.CourseType;
import ru.tsedrik.domain.Group;
import ru.tsedrik.domain.Person;
import ru.tsedrik.repository.CourseRepository;
import ru.tsedrik.repository.PersonRepository;

import java.util.Collection;
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
                System.currentTimeMillis(), courseDto.getCourseType(), courseDto.getCourseLocation(),
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
    public Collection<Course> getCourseByType(CourseType type) {
        return courseRepository.getCourseByCourseType(type);
    }

    @Override
    public CourseDto getCourseById(Long id) {
        CourseDto courseDto = courseRepository.findById(id)
                .map(course -> new CourseDto(
                        course.getId(), course.getCourseType().toString(), course.getCourseLocation(),
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
                course.getId(), course.getCourseType().toString(), course.getCourseLocation(),
                course.getBeginDate(), course.getEndDate(),
                course.getGroupCount(), course.getGroups()
        );

        return courseDto;
    }

}
