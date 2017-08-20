package org.happyreaction.services;

import org.happyreaction.model.Operation;
import org.happyreaction.repositories.OperationRepository;
import org.happyreaction.services.base.BaseService;
import org.happyreaction.services.base.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Service implementation for Operation.
 * 
 * @author Ignas
 *
 */
@org.springframework.stereotype.Service("operationService")
public class OperationService extends BaseService<Operation> implements Service<Operation> {

    private static final long serialVersionUID = 1L;

    /** Injected repository. */
    @Autowired
    private OperationRepository repository;
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected JpaRepository<Operation, Long> getRepository() {
        return (JpaRepository) repository;
    }
}
