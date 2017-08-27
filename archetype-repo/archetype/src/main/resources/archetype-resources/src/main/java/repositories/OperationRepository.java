#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repositories;

import ${package}.model.Operation;
import ${package}.repositories.custom.GenericRepository;

/**
 * Spring Data repository interface for {@link Operation} entity. Implementation is created by spring.
 * 
 * @author Ignas
 * 
 */
public interface OperationRepository extends GenericRepository<Operation, Long> {

}
