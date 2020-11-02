package ru.tsedrik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tsedrik.controller.dto.CourseDto;
import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.dao.PersonDAO;
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
    private PersonDAO personDAO;
    private GroupService groupService;

    @Value("${course.maxGroupCount}")
    private String maxGroupCount;

    @Value("${group.maxPersonPerGroup}")
    private String maxPersonPerGroup;

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
    public CourseDto deleteCourseById(Long id) {
        Course course = courseDAO.deleteById(id);
        CourseDto courseDto = null;
        if (course != null) {
            courseDto = new CourseDto(
                    course.getId(), course.getCourseType().toString(), course.getCourseLocation(),
                    course.getStartTime(), course.getEndTime(),
                    course.getGroupCount(), course.getGroups()
            );
        }

        return courseDto;
    }

    @Override
    public Collection<Course> getCourseByType(CourseType type) {
        return courseDAO.getByCourseType(type);
    }

    @Override
    public CourseDto getCourseById(Long id) {
        Course course = courseDAO.getById(id);

        if (course == null){
            throw new IllegalArgumentException("There is no course with id = " + id);
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
            throw new IllegalArgumentException("There is no course with id = " + courseId);
        }

        if (!course.isAvailableGroupExist()){
            throw new IllegalArgumentException("There is no available places on this course");
        }

        Person person = personDAO.getById(personId);
        if (person == null){
            throw new IllegalArgumentException("There is no person with id = " + personId);
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
