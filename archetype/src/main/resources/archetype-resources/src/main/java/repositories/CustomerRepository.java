#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repositories;

import ${package}.model.Customer;
import ${package}.repositories.custom.GenericRepository;

/**
 * Spring Data repository interface for {@link Customer} entity. Implementation is created by spring.
 * 
 * @author Ignas
 *
 */
public interface CustomerRepository extends GenericRepository<Customer, Long> {
}