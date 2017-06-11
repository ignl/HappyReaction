package org.happyreaction.services;

import java.util.List;

import org.happyreaction.model.Customer;
import org.happyreaction.model.CustomerPerk;
import org.happyreaction.repositories.CustomerRepository;
import org.happyreaction.services.base.BaseService;
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
public class CustomerService extends BaseService<Customer> implements ICustomerService {

    /** */
    private static final long serialVersionUID = 1L;
    
    /** Injected repository. */
    @Autowired
    private CustomerRepository repository;
    
    /**
     * @see org.happyreaction.services.base.BaseService#getRepository()
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected JpaRepository<Customer, Long> getRepository() {
        return (JpaRepository) repository;
    }

    /**
     * @see org.happyreaction.services.ICustomerService#getAllCustomerPerks()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<CustomerPerk> getAllCustomerPerks() {
        return em.createQuery("from CustomerPerk").getResultList();
    }

}
