package ru.tsedrik.dao.map;

import org.springframework.stereotype.Repository;
import ru.tsedrik.dao.LocationDAO;
import ru.tsedrik.model.CourseLocation;

import java.util.HashMap;

/**
 * Реализация интерфейса LocationDAO
 */
@Repository
public class LocationDAOImpl extends AbstactDAO<CourseLocation, Long> implements LocationDAO {

    public LocationDAOImpl() {
        super(new HashMap());
    }

}
