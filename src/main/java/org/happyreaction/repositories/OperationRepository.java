package org.happyreaction.repositories;

import org.happyreaction.model.Operation;
import org.happyreaction.repositories.custom.GenericRepository;

/**
 * Spring Data repository interface for {@link Operation} entity. Implementation is created by spring.
 * 
 * @author Ignas
 * 
 */
public interface OperationRepository extends GenericRepository<Operation, Long> {

}
