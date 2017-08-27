#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import ${package}.model.Operation;
import ${package}.repositories.OperationRepository;
import ${package}.services.base.BaseService;
import ${package}.services.base.Service;
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
