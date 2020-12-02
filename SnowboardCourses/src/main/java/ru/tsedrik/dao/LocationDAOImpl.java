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
import ru.tsedrik.model.CourseLocation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация интерфейса LocationDAO
 */
@Repository
public class LocationDAOImpl implements LocationDAO {

    private static final Logger log = LoggerFactory.getLogger(LocationDAOImpl.class.getName());

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public LocationDAOImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, SimpleJdbcInsert simpleJdbcInsert) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.simpleJdbcInsert = simpleJdbcInsert;
        this.simpleJdbcInsert.withTableName("location");
    }

    @Override
    public CourseLocation create(CourseLocation location) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", location.getId());
        params.put("name", location.getName());
        params.put("country", location.getCountry());
        params.put("city", location.getCity());

        int result = simpleJdbcInsert.execute(params);
        log.info("Was added " + result + " location.");

        return location;
    }

    @Override
    public CourseLocation getById(Long id) {
        try {
            return jdbcTemplate.queryForObject("select * from location where id = ?",
                    new Object[]{id}, new LocationRowMapper());
        } catch (EmptyResultDataAccessException e){
            log.info("There wan't found location with id = " + id);
            return null;
        }
    }

    @Override
    public Collection<CourseLocation> getAll() {
        return jdbcTemplate.query("SELECT * FROM location", new LocationRowMapper());
    }

    @Override
    public CourseLocation update(CourseLocation location) {
        final SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", location.getId())
                .addValue("name", location.getName())
                .addValue("country", location.getCountry())
                .addValue("city", location.getCity());

        int result = namedParameterJdbcTemplate.update("update location set name = :name" +
                ", country = :country, city = :city " +
                "where id = :id", namedParameters);

        log.info("Was updated " + result + " location.");

        return location;
    }

    @Override
    public CourseLocation delete(CourseLocation location) {
        return deleteById(location.getId()) ? location : null;
    }

    @Override
    public boolean deleteById(Long id) {

        int result = jdbcTemplate.update("update course set location_id = null where location_id = ?",
                new Object[]{id});

        log.info("Was updated " + result + " course.");

        result = jdbcTemplate.update("delete from location where id = ?", new Object[]{id});

        log.info("Was deleted " + result + " location.");

        return result > 0 ? true : false;
    }
}
