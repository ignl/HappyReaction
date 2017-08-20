package org.happyreaction.services;

import org.happyreaction.model.Account;
import org.happyreaction.repositories.AccountRepository;
import org.happyreaction.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Service implementation for Account.
 * 
 * @author Ignas
 *
 */
@Service("accountService")
public class AccountService extends BaseService<Account> {

    private static final long serialVersionUID = 1L;

    /** Injected repository. */
    @Autowired
    private AccountRepository repository;

    /**
     * @see org.happyreaction.services.base.BaseService#getRepository()
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected JpaRepository<Account, Long> getRepository() {
        return (JpaRepository) repository;
    }

}
