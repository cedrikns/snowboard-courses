package ru.tsedrik.dao.map;

import ru.tsedrik.dao.CourseDAO;
import ru.tsedrik.model.Course;
import ru.tsedrik.model.CourseType;
import ru.tsedrik.model.Group;
import ru.tsedrik.model.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CourseDAOImpl extends AbstactDAO<Course, Integer> implements CourseDAO {

    public CourseDAOImpl() {
        super(new HashMap<>());
    }

    @Override
    public Course getByGroup(Group group) {
        Course course = elements.values().stream().filter(c -> c.getGroups().contains(group)).findFirst().get();
        return course;
    }

    @Override
    public Collection<Course> getByInstructor(Person person) {
        List<Course> courses = new ArrayList<>();
        for (Course c : elements.values()){
            for(Group g : c.getGroups()){
                if (g.getInstructor().equals(person)){
                    courses.add(c);
                    break;
                }
            }
        }
        return courses;
    }

    @Override
    public Collection<Course> getByCourseType(CourseType type) {
        List<Course> courses = elements.values().stream().filter(c -> c.getCourseType() == type).collect(Collectors.toList());
        return courses;
    }
}
