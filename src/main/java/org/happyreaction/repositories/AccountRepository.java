package org.happyreaction.repositories;

import org.happyreaction.model.Account;
import org.happyreaction.repositories.custom.GenericRepository;

/**
 * Spring Data repository interface for {@link Account} entity. Implementation
 * created by spring.
 * 
 * @author Ignas
 * 
 */
public interface AccountRepository extends GenericRepository<Account, Long> {

}
