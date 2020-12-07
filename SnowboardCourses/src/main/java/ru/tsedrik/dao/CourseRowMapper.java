package ru.tsedrik.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.tsedrik.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper from ResultSet to Course
 */
public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        final Course course = new Course();
        course.setId(resultSet.getLong("course_id"));
        course.setCourseType(CourseType.valueOf(resultSet.getString("course_type").toUpperCase()));
        if (resultSet.getLong("location_id") != 0){
            CourseLocation location = new CourseLocation();
            location.setId(resultSet.getLong("course_id"));
            location.setName(resultSet.getString("name"));
            location.setCountry(resultSet.getString("country"));
            location.setCity(resultSet.getString("city"));
            course.setCourseLocation(location);
        }
        course.setBeginDate(resultSet.getDate("begin_date").toLocalDate());
        course.setEndDate(resultSet.getDate("end_date").toLocalDate());
        course.setGroupCount(resultSet.getInt("max_groups_num"));

        return course;
    }
}
