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
import ru.tsedrik.model.Group;
import ru.tsedrik.model.Person;

import java.util.Collection;

/**
 * Реализация интерфейса GroupDAO
 */
@Repository
public class GroupDAOImpl implements GroupDAO {

    private static final Logger log = LogManager.getLogger(GroupDAOImpl.class.getName());

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public GroupDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
        this.simpleJdbcInsert.withTableName("\"group\"");
    }

    @Override
    public Group create(Group group) {

        Long personId = null;
        if (group.getInstructor() != null){
            personId = group.getInstructor().getId();
        }
        int result = jdbcTemplate.update("insert into \"group\" values (?, ?, ?, ?, ?)",
                group.getId(), group.getCourseId(), personId, group.getTotalNumberOfPlaces(),
                group.getAvailableNumberOfPlaces());

        log.info("Was added " + result + " group.");

        return group;
    }

    @Override
    public Group getById(Long id) {
        try {
            return jdbcTemplate.queryForObject("select g.id as group_id, course_id, person_id, " +
                            "places_num_total, places_num_available, first_name, last_name, email, role " +
                            "from \"group\" g left join person p on g.person_id = p.id " +
                            "where g.id = ?",
                    new Object[]{id}, new GroupRowMapper());
        } catch (EmptyResultDataAccessException e){
            log.info("There wan't found group with id = " + id);
            return null;
        }
    }

    @Override
    public Collection<Group> getAll() {
        return jdbcTemplate.query("select g.id as group_id, course_id, person_id, " +
                "places_num_total, places_num_available, first_name, last_name, email, role " +
                "from \"group\" g left join person p on g.person_id = p.id", new GroupRowMapper());
    }

    @Override
    public Group update(Group group) {
        Long personId = null;
        if (group.getInstructor() != null){
            personId = group.getInstructor().getId();
        }

        final SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", group.getId())
                .addValue("course_id", group.getCourseId())
                .addValue("person_id", personId)
                .addValue("places_num_total", group.getTotalNumberOfPlaces())
                .addValue("places_num_available", group.getAvailableNumberOfPlaces());

        int result = namedParameterJdbcTemplate.update("update \"group\" set course_id = :course_id, " +
                "person_id = :person_id, places_num_total = :places_num_total, " +
                "places_num_available = :places_num_available " +
                "where id = :id", namedParameters);

        log.info("Was updated " + result + " group.");

        return group;
    }

    @Override
    public Group delete(Group group) {

        return deleteById(group.getId()) ? group : null;
    }

    @Override
    public boolean deleteById(Long id) {

        int result = jdbcTemplate.update("delete from group_person where group_id = ?", new Object[]{id});
        log.info("Was deleted " + result + " связок group-person");

        result = jdbcTemplate.update("delete from \"group\" where id = ?", new Object[]{id});
        log.info("Was deleted " + result + " group.");

        return result > 0 ? true : false;
    }

    @Override
    public Collection<Group> getAllByInstructor(Person person) {
        return jdbcTemplate.query("select g.id as group_id, course_id, person_id, " +
                        "places_num_total, places_num_available, first_name, last_name, email, role " +
                        "from \"group\" g left join person p on g.person_id = p.id " +
                        "where person_id = ?",
                new Object[]{person.getId()}, new GroupRowMapper());
    }

    @Override
    public Collection<Group> getAllByCourseId(Long id) {
        return jdbcTemplate.query("select g.id as group_id, course_id, person_id, " +
                        "places_num_total, places_num_available, first_name, last_name, email, role " +
                        "from \"group\" g left join person p on g.person_id = p.id " +
                        "where course_id = ?",
                new Object[]{id}, new GroupRowMapper());
    }

    @Override
    public boolean addPersonToGroup(Long groupId, Long personId) {
        int result = jdbcTemplate.update("insert into group_person (group_id, person_id) values (?, ?)", groupId, personId);
        log.info("Was added " + result + " связка group-person.");

        return result > 0 ? true : false;

    }
}
