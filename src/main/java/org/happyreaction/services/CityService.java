package org.happyreaction.services;

import org.happyreaction.model.City;
import org.happyreaction.repositories.CityRepository;
import org.happyreaction.services.base.BaseService;
import org.happyreaction.services.base.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Service implementation for City.
 * 
 * @author Ignas
 *
 */
@org.springframework.stereotype.Service("cityService")
public class CityService extends BaseService<City> implements Service<City> {

    private static final long serialVersionUID = 1L;

    /** Injected repository. */
    @Autowired
    private CityRepository repository;
    
    /**
     * @see org.happyreaction.services.base.BaseService#getRepository()
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected JpaRepository<City, Long> getRepository() {
        return (JpaRepository) repository;
    }
}
