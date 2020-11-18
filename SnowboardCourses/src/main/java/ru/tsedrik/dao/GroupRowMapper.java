package ru.tsedrik.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.tsedrik.model.Group;
import ru.tsedrik.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRowMapper implements RowMapper<Group> {

    @Override
    public Group mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        final Group group = new Group();
        group.setId(resultSet.getLong("group_id"));
        group.setCourseId(resultSet.getLong("course_id"));
        if (resultSet.getLong("person_id") != 0){
            Person person = new Person();
            person.setId(resultSet.getLong("person_id"));
            person.setFirstName(resultSet.getString("first_name"));
            person.setLastName(resultSet.getString("last_name"));
            person.setEmail(resultSet.getString("email"));
            person.setRole(resultSet.getString("role"));
            group.setInstructor(person);
        }
        group.setTotalNumberOfPlaces(resultSet.getInt("places_num_total"));
        group.setAvailableNumberOfPlaces(resultSet.getInt("places_num_available"));

        return group;
    }
}
