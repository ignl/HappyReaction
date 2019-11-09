package org.happyreaction.repositories;

import org.happyreaction.model.City;
import org.happyreaction.repositories.custom.GenericRepository;

/**
 * Spring Data repository interface for {@link City} entity. Implementation is created by spring.
 */
public interface CityRepository extends GenericRepository<City, Long> {

}
