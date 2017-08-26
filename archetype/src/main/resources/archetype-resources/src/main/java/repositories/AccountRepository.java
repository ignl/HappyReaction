#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repositories;

import ${package}.model.Account;
import ${package}.repositories.custom.GenericRepository;

/**
 * Spring Data repository interface for {@link Account} entity. Implementation is created by spring.
 * 
 * @author Ignas
 * 
 */
public interface AccountRepository extends GenericRepository<Account, Long> {

}
