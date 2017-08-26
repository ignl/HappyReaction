#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ${package}.model.base.BaseEntity;
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
@Service("dynamicTypeService")
public class DynamicTypeService implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /** JPA entity manager. */
    @PersistenceContext
    private EntityManager em;

    /**
     * Loads entity by its id.
     *
     * @param entityClass
     *            Concrete Entity class.
     * @param id
     *            Entity ID
     * @return Loaded Entity.
     */
    @SuppressWarnings("rawtypes")
    public BaseEntity findById(Class entityClass, Long id) {
        List list = em.createQuery("from " + entityClass.getName() + " where id=:id").setParameter("id", id).getResultList();
        return list.size() > 0 ? (BaseEntity) list.get(0) : null;
    }

}
