#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import ${package}.model.City;
import ${package}.repositories.CityRepository;
import ${package}.services.base.BaseService;
import ${package}.services.base.Service;
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
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected JpaRepository<City, Long> getRepository() {
        return (JpaRepository) repository;
    }
}
