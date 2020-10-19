package ru.tsedrik.dao.map;

import ru.tsedrik.dao.LocationDAO;
import ru.tsedrik.model.CourseLocation;

import java.util.HashMap;

public class LocationDAOImpl extends AbstactDAO<CourseLocation, Integer> implements LocationDAO {

    public LocationDAOImpl() {
        super(new HashMap());
    }

}
