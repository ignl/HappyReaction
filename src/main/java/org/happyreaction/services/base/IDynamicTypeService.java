package org.happyreaction.services.base;

import org.happyreaction.model.base.BaseEntity;
import org.happyreaction.model.base.IEntity;

/**
 * Service interface for service with no concrete Entity behind.
 * 
 * @author Ignas
 * 
 */
public interface IDynamicTypeService {
    
    /**
     * Loads entity by its id.
     * 
     * @param entityClass
     *            Concrete Entity class.
     * @param id
     *            Entity ID
     * @return Loaded Entity.
     */
    BaseEntity findById(Class entityClass, Long id);

}
