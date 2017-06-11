package org.happyreaction.repositories;

import org.happyreaction.model.Customer;
import org.happyreaction.repositories.custom.GenericRepository;

/**
 * Spring Data repository interface for {@link Customer} entity. Implementation created by spring.
 * 
 * @author Ignas
 *
 */
public interface CustomerRepository extends GenericRepository<Customer, Long> {
}