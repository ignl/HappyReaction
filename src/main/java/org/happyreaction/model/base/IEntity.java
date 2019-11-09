package org.happyreaction.model.base;

/**
 * Interface for all JPA entities to implement.
 */
public interface IEntity {
    
    /**
     * All entities must have an ID field.
     */
    Long getId();
    
    /**
     * @return True if entity is not yet saved to the database.
     */
    boolean isTransient();

}