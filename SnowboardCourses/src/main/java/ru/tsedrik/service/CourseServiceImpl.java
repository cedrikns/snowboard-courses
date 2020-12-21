package ru.tsedrik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.stream.Collectors;

/**
 * Реализация интерфейса CourseService
 */
@Service
@Transactional
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

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class.getName());

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

        Course course = new Course(
                System.currentTimeMillis(), courseDto.getCourseType(), courseDto.getCourseLocation(),
                courseDto.getStartTime(), courseDto.getEndTime(),
                courseDto.getGroupCount()
        );

        courseDAO.create(course);

        for (int i = 0; i < course.getGroupCount(); i++){
            Group group = new Group(System.currentTimeMillis(), course.getId(), Integer.parseInt(maxPersonPerGroup));
            groupService.addGroup(group);
            course.getGroups().add(group);
        }

        courseDto.setId(course.getId());
        courseDto.setGroups(course.getGroups());

        return courseDto;
    }

    @Override
    public Course deleteCourse(Course course) {
        course.getGroups().forEach(g -> groupService.deleteGroup(g));
        return courseDAO.delete(course);
    }

    @Override
    public boolean deleteCourseById(Long id) {
        groupService.getAllByCourseId(id).forEach(g -> groupService.deleteGroup(g));
        return courseDAO.deleteById(id);
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

        course.setGroups(groupService.getAllByCourseId(course.getId()).stream().collect(Collectors.toList()));

        CourseDto courseDto = new CourseDto(
                course.getId(), course.getCourseType().toString(), course.getCourseLocation(),
                course.getBeginDate(), course.getEndDate(),
                course.getGroupCount(), course.getGroups()
        );

        return courseDto;
    }

    public CourseDto enroll(Long courseId, Long personId){
        Course course = courseDAO.getById(courseId);

        if (course == null){
            throw new CourseNotFoundException(courseNotFoundExMsg + courseId);
        }

        course.setGroups(groupService.getAllByCourseId(course.getId()).stream().collect(Collectors.toList()));

        if (!course.isAvailableGroupExist()){
            throw new IllegalArgumentException("There is no available places on this course");
        }

        Person person = personDAO.getById(personId);
        if (person == null){
            throw new PersonNotFoundException(personNotFoundExMsg + "with id = " + personId);
        }

        Group group = course.getAvailableGroup();
        List<Person> students = group.getStudents();
        students.add(person);
        group.setStudents(students);
        group.setAvailableNumberOfPlaces(group.getAvailableNumberOfPlaces() - 1);

        groupService.addPersonToGroup(group.getId(), personId);
        groupService.updateGroup(group);

        CourseDto courseDto = new CourseDto(
                course.getId(), course.getCourseType().toString(), course.getCourseLocation(),
                course.getBeginDate(), course.getEndDate(),
                course.getGroupCount(), course.getGroups()
        );

        return courseDto;
    }

}
