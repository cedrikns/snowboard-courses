package ru.tsedrik.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tsedrik.controller.dto.CourseDto;
import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.dao.PersonDAO;
import ru.tsedrik.exception.CourseNotFoundException;
import ru.tsedrik.exception.PersonNotFoundException;
import ru.tsedrik.model.Course;
import ru.tsedrik.model.CourseType;
import ru.tsedrik.model.Group;
import ru.tsedrik.model.Person;

import java.util.Collection;
import java.util.List;

/**
 * Реализация интерфейса CourseService
 */
@Service
public class CourseServiceImpl implements CourseService{

    /**
     * Объект для управления персистентным состоянием объектов типа Course
     */
    private CourseDAO courseDAO;

    /**
     * Объект для управления персистентным состоянием объектов типа Person
     */
    private PersonDAO personDAO;

    /**
     * Объект для управления обращения к сервису сущности Group
     */
    private GroupService groupService;

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

    private static final Logger logger = LogManager.getLogger(CourseServiceImpl.class.getName());

    public CourseDAO getCourseDAO() {
        return courseDAO;
    }

    @Autowired
    public void setCourseDAO(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Autowired
    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Autowired
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public CourseDto addCourse(CourseDto courseDto) {

//        groupService.asyncMethod();

        logger.info("Work after asyncMethod was called.");

        Course course = new Course(
                System.currentTimeMillis(), courseDto.getCourseType(), courseDto.getCourseLocation(),
                courseDto.getStartTime(), courseDto.getEndTime(),
                courseDto.getGroupCount()
        );

        for (int i = 0; i < course.getGroupCount(); i++){
            Group group = new Group(System.currentTimeMillis(), Integer.parseInt(maxPersonPerGroup));
            groupService.addGroup(group);
            course.getGroups().add(group);
        }

        courseDAO.create(course);

        courseDto.setId(course.getId());
        courseDto.setGroups(course.getGroups());

        return courseDto;
    }

    @Override
    public Course deleteCourse(Course course) {
        return courseDAO.delete(course);
    }

    @Override
    public boolean deleteCourseById(Long id) {
        Course course = courseDAO.deleteById(id);
        boolean deletingResult;
        if (course != null) {
            deletingResult = true;
        } else {
            deletingResult = false;
        }

        return deletingResult;
    }

    @Override
    public Collection<Course> getCourseByType(CourseType type) {
        return courseDAO.getByCourseType(type);
    }

    @Override
    public CourseDto getCourseById(Long id) {
        Course course = courseDAO.getById(id);

        if (course == null){
            throw new CourseNotFoundException(courseNotFoundExMsg + id);
        }
        CourseDto courseDto = new CourseDto(
                course.getId(), course.getCourseType().toString(), course.getCourseLocation(),
                course.getStartTime(), course.getEndTime(),
                course.getGroupCount(), course.getGroups()
        );

        return courseDto;
    }

    public CourseDto enroll(Long courseId, Long personId){
        Course course = courseDAO.getById(courseId);

        if (course == null){
            throw new CourseNotFoundException(courseNotFoundExMsg + courseId);
        }

        if (!course.isAvailableGroupExist()){
            throw new IllegalArgumentException("There is no available places on this course");
        }

        Person person = personDAO.getById(personId);
        if (person == null){
            throw new PersonNotFoundException(personNotFoundExMsg + personId);
        }

        Group group = course.getAvailableGroup();
        List<Person> students = group.getStudents();
        students.add(person);
        group.setStudents(students);
        group.setAvailableNumberOfPlaces(group.getAvailableNumberOfPlaces() - 1);

        groupService.updateGroup(group);

        CourseDto courseDto = new CourseDto(
                course.getId(), course.getCourseType().toString(), course.getCourseLocation(),
                course.getStartTime(), course.getEndTime(),
                course.getGroupCount(), course.getGroups()
        );

        return courseDto;
    }

}
