package ru.tsedrik.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.tsedrik.model.Person;
import ru.tsedrik.model.Role;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация интерфейса PersonDAO
 */
@Repository
public class PersonDAOImpl implements PersonDAO {

    private static final Logger log = LogManager.getLogger(PersonDAOImpl.class.getName());

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public PersonDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("person");
    }

    @Override
    public Person create(Person person) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", person.getId());
        params.put("first_name", person.getFirstName());
        params.put("last_name", person.getLastName());
        params.put("email", person.getEmail());
        params.put("role", person.getRole().name());
        int result = simpleJdbcInsert.execute(params);
        log.info("Was added " + result + " person.");

        return person;
    }

    @Override
    public Person getById(Long id) {
        try {
            return jdbcTemplate.queryForObject("select * from person where id = ?",
                    new Object[]{id}, new PersonRowMapper());
        } catch (EmptyResultDataAccessException e){
            log.info("There wan't found person with id = " + id);
            return null;
        }
    }

    @Override
    public Collection<Person> getAll() {
        return jdbcTemplate.query("SELECT * FROM person", new PersonRowMapper());
    }

    @Override
    public Person update(Person person) {
        final SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", person.getId())
                .addValue("first_name", person.getFirstName())
                .addValue("last_name", person.getLastName())
                .addValue("email", person.getEmail())
                .addValue("role", person.getRole().name());

        int result = namedParameterJdbcTemplate.update("update person set first_name = :first_name" +
                ", last_name = :last_name, email = :email, role = :role " +
                "where id = :id", namedParameters);

        log.info("Was updated " + result + " person.");

        return person;
    }

    @Override
    public Person delete(Person person) {

        return deleteById(person.getId()) ? person : null;
    }

    @Override
    public boolean deleteById(Long id) {

        int result = jdbcTemplate.update("update \"group\" set person_id = null where person_id = ?", new Object[]{id});
        log.info("Was updated " + result + " group.");

        result = jdbcTemplate.update("delete from group_person where person_id = ?", new Object[]{id});
        log.info("Was deleted " + result + " связок group-person");

        result = jdbcTemplate.update("delete from person where id = ?", new Object[]{id});
        log.info("Was deleted " + result + " person.");

        return result > 0 ? true : false;
    }

    @Override
    public List<Person> getAllByRole(Role role) {
        List<Person> personsWithRole = jdbcTemplate.query("select * from person where role = ?"
                , new Object[]{role.name()}, new PersonRowMapper());

        log.info("Was founded " + personsWithRole.size() + " persons with role " + role);

        return personsWithRole;
    }

    @Override
    public Person getPersonByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject("select * from person where email = ?",
                    new Object[]{email}, new PersonRowMapper());
        } catch (EmptyResultDataAccessException e){
            log.info("There wan't found person with email = " + email);
            return null;
        }
    }

    @Override
    public List<Person> getAllByEmailAndRole(String email, Role role) {
        List<Person> persons = jdbcTemplate.query("select * from person where email = ? and role = ?",
                    new Object[]{email, role.name()}, new PersonRowMapper());

        log.info("Was founded " + persons.size() + " persons with role " + role + " and email " + email + ".");
        return persons;
    }

    @Override
    public Collection<Person> getAllByGroupId(Long groupId) {
        List<Person> personsFromGroup = jdbcTemplate.query(
                "select p.id as id, first_name, last_name, email, role " +
                        "from person p join group_person gp on p.id = gp.person_id " +
                        "where group_id = ?"
                , new Object[]{groupId}, new PersonRowMapper());

        log.info("Was founded " + personsFromGroup.size() + " persons in group " + groupId);

        return personsFromGroup;
    }
}
