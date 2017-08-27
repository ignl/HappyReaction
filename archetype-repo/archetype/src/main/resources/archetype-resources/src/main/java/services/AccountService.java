#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import ${package}.model.Account;
import ${package}.repositories.AccountRepository;
import ${package}.services.base.BaseService;
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
     * {@inheritdoc}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected JpaRepository<Account, Long> getRepository() {
        return (JpaRepository) repository;
    }

}
