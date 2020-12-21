package ru.tsedrik.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import ru.tsedrik.model.Person;
import ru.tsedrik.model.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper from ResultSet to Person
 */
public class PersonRowMapper implements RowMapper<Person> {

    private static final Logger log = LoggerFactory.getLogger(PersonRowMapper.class.getName());

    @Override
    public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        final Person person = new Person();
        person.setId(resultSet.getLong("id"));
        person.setFirstName(resultSet.getString("first_name"));
        person.setLastName(resultSet.getString("last_name"));
        person.setEmail(resultSet.getString("email"));
        person.setRole(Role.valueOf(resultSet.getString("role")));

        return person;
    }
}
