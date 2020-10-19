package ru.tsedrik.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.tsedrik.model.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainService {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        PersonService personService = (PersonService) context.getBean("personService");
        GroupService groupService = (GroupService) context.getBean("groupService");
        CourseService courseService = (CourseService) context.getBean("courseService");

        Person instructor = new Person();
        instructor.setId(1);
        instructor.setRole(Role.INSTRUCTOR);
        instructor.setFirstName("Елена");
        instructor.setLastName("Азарова");
        instructor.setEmail("azarova@gmail.com");
        System.out.println("Before save: " + instructor);
        personService.addPerson(instructor);

        System.out.println("After save: " + personService.getPersonById(1));

        personService.deletePerson(instructor);
        System.out.println("After delete: " + personService.getPersonById(1));

        Person student1 = new Person();
        student1.setId(1);
        student1.setRole(Role.STUDENT);
        student1.setFirstName("Иван");
        student1.setLastName("Сидоров");
        student1.setEmail("sidorov@gmail.com");
        personService.addPerson(student1);

        Person student2 = new Person();
        student2.setId(1);
        student2.setRole(Role.STUDENT);
        student2.setFirstName("Алина");
        student2.setLastName("Лушакина");
        student2.setEmail("lushakina@gmail.com");
        personService.addPerson(student2);

        Set<Person> students = new HashSet<>();
        students.add(student1);
        students.add(student2);

        Group group = new Group();
        group.setId(11);
        group.setInstructor(instructor);
        group.setStudents(students);
        groupService.addGroup(group);
        System.out.println(groupService.getGroupById(11));

        Course course = new Course();
        course.setId(22);
        course.setCourseType(CourseType.CARVING);
        course.setStartTime(LocalDate.of(2020, 12, 05));
        course.setEndTime(LocalDate.of(2020, 12, 11));
        course.setGroupCount(1);
        course.setGroups(Arrays.asList(group));
        courseService.addCourse(course);
        System.out.println(courseService.getCourseById(22));

    }

}
