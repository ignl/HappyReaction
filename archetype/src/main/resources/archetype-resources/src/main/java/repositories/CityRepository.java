#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repositories;

import ${package}.model.City;
import ${package}.repositories.custom.GenericRepository;

/**
 * Spring Data repository interface for {@link City} entity. Implementation is created by spring.
 * 
 * @author Ignas
 * 
 */
public interface CityRepository extends GenericRepository<City, Long> {

}
