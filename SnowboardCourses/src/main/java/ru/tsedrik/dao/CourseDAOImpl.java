package ru.tsedrik.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.tsedrik.model.Course;
import ru.tsedrik.model.CourseType;
import ru.tsedrik.model.Person;

import java.time.LocalDate;
import java.util.*;

/**
 * Реализация интерфейса CourseDAO
 */
@Repository
public class CourseDAOImpl implements CourseDAO {

    private static final Logger log = LoggerFactory.getLogger(CourseDAOImpl.class.getName());

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public CourseDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
        this.simpleJdbcInsert.withTableName("course");
    }

    @Override
    public Course create(Course course) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", course.getId());
        params.put("course_type", course.getCourseType());
        if (course.getCourseLocation() != null){
            params.put("location_id", course.getCourseLocation().getId());
        }
        params.put("begin_date", course.getBeginDate());
        params.put("end_date", course.getEndDate());
        params.put("max_groups_num", course.getGroupCount());

        int result = simpleJdbcInsert.execute(params);
        log.info("Was added " + result + " course.");

        return course;
    }

    @Override
    public Course getById(Long id) {
        try {
            return jdbcTemplate.queryForObject("select c.id as course_id, course_type, location_id, " +
                            "begin_date, end_date, max_groups_num, name, country, city " +
                            "from course c left join location l on c.location_id = l.id " +
                            "where c.id = ?",
                    new Object[]{id}, new CourseRowMapper());
        } catch (EmptyResultDataAccessException e){
            log.info("There wan't found course with id = " + id);
            return null;
        }
    }

    @Override
    public Collection<Course> getAll() {
        return jdbcTemplate.query("select c.id as course_id, course_type, location_id, " +
                "begin_date, end_date, max_groups_num, name, country, city " +
                "from course c left join location l on c.location_id = l.id ", new CourseRowMapper());
    }

    @Override
    public Course update(Course course) {
        Long locationId = null;
        if (course.getCourseLocation() != null){
            locationId =  course.getCourseLocation().getId();
        }
        final SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", course.getId())
                .addValue("course_type", course.getCourseType().name())
                .addValue("location_id", locationId)
                .addValue("begin_date", course.getBeginDate())
                .addValue("end_date", course.getEndDate())
                .addValue("max_groups_num", course.getGroupCount());

        int result = namedParameterJdbcTemplate.update("update course set course_type = :course_type, " +
                "location_id = :location_id, begin_date = :begin_date, " +
                "end_date = :end_date, max_groups_num = :max_groups_num " +
                "where id = :id", namedParameters);

        log.info("Was updated " + result + " course.");

        return course;
    }

    @Override
    public Course delete(Course course) {

        return deleteById(course.getId()) ? course : null;
    }

    @Override
    public boolean deleteById(Long id) {
        int result = jdbcTemplate.update("delete from course where id = ?", new Object[]{id});

        log.info("Was deleted " + result + " course.");

        return result > 0 ? true : false;
    }

    @Override
    public Collection<Course> getByInstructor(Person person) {
        return jdbcTemplate.query("select c.id as course_id, course_type, location_id, " +
                "begin_date, end_date, max_groups_num, name, country, city " +
                "from course c left join location l on c.location_id = l.id " +
                "where c.id in (select course_id from \"group\" where person_id = ?)",
                new Object[]{person.getId()}, new CourseRowMapper());
    }

    @Override
    public Collection<Course> getByCourseType(CourseType type) {
        return jdbcTemplate.query("select c.id as course_id, course_type, location_id, " +
                        "begin_date, end_date, max_groups_num, name, country, city " +
                        "from course c left join location l on c.location_id = l.id " +
                        "where course_type = ?)",
                new Object[]{type.name()}, new CourseRowMapper());
    }

    @Override
    public Collection<Course> getByCourseTypeAndDate(CourseType type, LocalDate beginDate, LocalDate endDate) {
        return jdbcTemplate.query("select c.id as course_id, course_type, location_id, " +
                        "begin_date, end_date, max_groups_num, name, country, city " +
                        "from course c left join location l on c.location_id = l.id " +
                        "where course_type = ? and begin_date >= ? and end_date <= ?)",
                new Object[]{type.name(), beginDate, endDate}, new CourseRowMapper());
    }
}
