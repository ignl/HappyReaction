package org.happyreaction.services;

import org.happyreaction.model.Operation;
import org.happyreaction.repositories.OperationRepository;
import org.happyreaction.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Service implementation for Operation.
 * 
 * @author Ignas
 *
 */
@Service("operationService")
public class OperationService extends BaseService<Operation> implements IOperationService {

    /** */
    private static final long serialVersionUID = 1L;

    /** Injected repository. */
    @Autowired
    private OperationRepository repository;
    
    /**
     * @see org.happyreaction.services.base.BaseService#getRepository()
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected JpaRepository<Operation, Long> getRepository() {
        return (JpaRepository) repository;
    }
}
