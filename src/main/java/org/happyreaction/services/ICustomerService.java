package org.happyreaction.services;

import java.util.List;

import org.happyreaction.model.Customer;
import org.happyreaction.model.CustomerPerk;
import org.happyreaction.services.base.IService;

/**
 * Customer service interface.
 * 
 * @author Ignas
 * 
 */
public interface ICustomerService extends IService<Customer> {

    /**
     * Loads all customer perks.
     */
    List<CustomerPerk> getAllCustomerPerks();

}
