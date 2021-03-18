package ru.tsedrik.service;

import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.tsedrik.domain.*;
import ru.tsedrik.exception.CourseNotFoundException;
import ru.tsedrik.exception.PersonNotFoundException;
import ru.tsedrik.repository.CourseRepository;
import ru.tsedrik.repository.PersonRepository;
import ru.tsedrik.resource.dto.CourseDto;
import ru.tsedrik.resource.dto.CourseSearchDto;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

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
     * Объект для маппинга между классами Course и CourseDto
     */
    private MapperFacade mapperFacade;

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

    @Autowired
    public void setMapperFacade(MapperFacade mapperFacade){
        this.mapperFacade = mapperFacade;
    }

    @Override
    public Mono<CourseDto> addCourse(CourseDto courseDto) {

        if ((courseDto.getBeginDate().isBefore(LocalDate.now())) || (courseDto.getEndDate().isBefore(LocalDate.now()))){
            throw new IllegalArgumentException("Begin and End date of course must be greater than current");
        }

        Course course = mapperFacade.map(courseDto, Course.class);
        course.setId(System.currentTimeMillis());
        course.setCourseStatus(CourseStatus.OPEN);

        Set<Group> groups = new HashSet<>();
        for (int i = 0; i < course.getGroupCount(); i++){
            Group group = new Group(System.currentTimeMillis() + i + 1, course.getId(), Integer.parseInt(maxPersonPerGroup));
            groups.add(group);
        }

        course.setGroups(groups);

        Mono<Void> dbRequest = Mono.fromRunnable(() -> courseRepository.save(course));
        return Mono.when(dbRequest).then(Mono.fromSupplier(() -> mapperFacade.map(course, CourseDto.class)));
    }

    @Override
    public Mono<Boolean> deleteCourse(Course course) {
        return Mono.fromSupplier(() -> {
            courseRepository.delete(course);
            return true;
        });
    }

    @Override
    public Mono<Boolean> deleteCourseById(Long id) {
        return Mono.fromSupplier(() -> {
            courseRepository.deleteById(id);
            return true;
        });
    }

    @Override
    public Mono<CourseDto> getCourseById(Long id) {
        return Mono.fromSupplier(() ->
            courseRepository.findById(id)
                    .map(course -> mapperFacade.map(course, CourseDto.class))
                    .orElseThrow(() -> new CourseNotFoundException(courseNotFoundExMsg + id))
        );
    }

    public Mono<CourseDto> enroll(Long courseId, Long personId){
        Mono<Course> foundedCourse = Mono.fromSupplier(() -> {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new CourseNotFoundException(courseNotFoundExMsg + courseId));

            if (course.getCourseStatus() == CourseStatus.CLOSE){
                throw new IllegalArgumentException("This course is closed. Please, choose another one.");
            }
            if (!course.isAvailableGroupExist()){
                throw new IllegalArgumentException("There is no available places on this course");
            }

            boolean isPersonNotExistInCourse = course.getGroups().stream()
                    .flatMap(group -> group.getStudents().stream())
                    .noneMatch(p -> p.getId().equals(personId));

            if (!isPersonNotExistInCourse){
                throw new IllegalArgumentException("Person with id = " + personId + " is already enrolled on course with id = " + courseId);
            }
            return course;
        });

        Mono<Person> foundedPerson = Mono.fromSupplier(() -> {
            Person person = personRepository.findById(personId)
                    .orElseThrow(() -> new PersonNotFoundException(personNotFoundExMsg + "with id = " + personId));
            return person;
        });

        BiFunction<Course, Person, CourseDto> updateCoursDto = (course, person) -> {
            Group group = course.getAvailableGroup();
            Set<Person> students = group.getStudents();
            if (students.add(person)) {
                group.setStudents(students);
                group.setAvailableNumberOfPlaces(group.getTotalNumberOfPlaces() - students.size());
            } else {
                throw new IllegalArgumentException("Участник " + person.getId() + " не был добавлен на курс.");
            }

            courseRepository.save(course);
            CourseDto courseDto = mapperFacade.map(course, CourseDto.class);
            return courseDto;
        };

        return Mono.when(foundedCourse, foundedPerson).then(Mono.zip(foundedCourse, foundedPerson, updateCoursDto));
    }

    @Override
    public Flux<CourseDto> getCourses(CourseSearchDto courseSearchDto, Pageable pageable) {
        return Flux.fromIterable(courseRepository.findAll(getSpecification(courseSearchDto), pageable)
                .map(course -> mapperFacade.map(course, CourseDto.class))
                .toList()
        );
    }

    @Override
    public void changeOldCourseStatus(){
        courseRepository.updateClosedCourseStatus(LocalDate.now(), CourseStatus.CLOSE);
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

            predicates.add(builder.not(root.get("courseStatus").in(CourseStatus.CLOSE)));

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
}
