package ru.tsedrik.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.tsedrik.model.CourseLocation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationRowMapper implements RowMapper<CourseLocation> {

    @Override
    public CourseLocation mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        final CourseLocation location = new CourseLocation();
        location.setId(resultSet.getLong("id"));
        location.setName(resultSet.getString("name"));
        location.setCountry(resultSet.getString("country"));
        location.setCity(resultSet.getString("city"));

        return location;
    }
}
