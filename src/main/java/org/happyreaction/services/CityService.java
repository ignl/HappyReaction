package org.happyreaction.services;

import org.happyreaction.model.City;
import org.happyreaction.repositories.CityRepository;
import org.happyreaction.services.base.BaseService;
import org.happyreaction.services.base.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Service implementation for City.
 */
@org.springframework.stereotype.Service("cityService")
public class CityService extends BaseService<City> implements Service<City> {

    private static final long serialVersionUID = 1L;

    @Autowired
    private CityRepository repository;
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected JpaRepository<City, Long> getRepository() {
        return repository;
    }
}
