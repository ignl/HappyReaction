package org.happyreaction.services.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.happyreaction.model.base.BaseEntity;
import org.happyreaction.model.base.IEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service class that provides some standard dao methods for any entity. Hoverer
 * it must pass entity class together with other parameters. 
 * P.S. Currently only one method from BaseService is implemented but its easy to add any method
 * from generic service same way.
 * 
 * @author Ignas
 * 
 */
@Transactional(readOnly = true)
@Service("variableTypeService")
public class VariableTypeService implements IVariableTypeService, Serializable {
    
    /**
     * Class version id for serialization. After a change to serialized field this number should be changed so it would
     * be clear its different class version.
     */
    private static final long serialVersionUID = 1L;
    
    /** JPA entity manager. */
    @PersistenceContext
    private EntityManager em;

    /**
     * @see org.happyreaction.services.base.IVariableTypeService#findById(java.lang.Class, java.lang.Long)
     */
    @SuppressWarnings("rawtypes")
    public BaseEntity findById(Class<? extends IEntity> entityClass, Long id) {
        List list = em.createQuery("from " + entityClass.getName() + " where id=?").setParameter(1, id).getResultList();
        return list.size() > 0 ? (BaseEntity) list.get(0) : null;
    }

}
