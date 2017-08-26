#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.base;

/**
 * Interface for all JPA entities to implement.
 * 
 * @author Ignas
 *
 */
public interface IEntity {
    
    /**
     * All entities must have an ID field.
     */
    Long getId();
    
    /**
     * Returns if entity is already saved in database.
     */
    boolean isTransient();

}