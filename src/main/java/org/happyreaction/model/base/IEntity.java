package org.happyreaction.model.base;

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