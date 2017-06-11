package org.happyreaction.model.base;

import java.io.Serializable;

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
    Serializable getId();
    
    /**
     * Returns if entity is already saved in database.
     */
    boolean isTransient();

}