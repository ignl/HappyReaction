#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services;

import ${package}.model.Customer;
import ${package}.repositories.CustomerRepository;
import ${package}.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Service implementation for Customer.
 * 
 * @author Ignas
 *
 */
@Service("customerService")
public class CustomerService extends BaseService<Customer> {

    private static final long serialVersionUID = 1L;
    
    /** Injected repository. */
    @Autowired
    private CustomerRepository repository;
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected JpaRepository<Customer, Long> getRepository() {
        return (JpaRepository) repository;
    }

}
