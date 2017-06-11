package org.happyreaction.rest.controller;

import org.happyreaction.model.Customer;
import org.happyreaction.rest.controller.base.CrudController;
import org.happyreaction.services.ICustomerService;
import org.happyreaction.services.base.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST Webservices controller for Customers. All supported WS methods are mapped to this controller.
 * 
 * @author Ignas
 * 
 */
@Controller
@RequestMapping("/customer")
public class CustomerRestController extends CrudController<Customer> {

    /** Injected service. */
    @Autowired
    private ICustomerService customerService;

	/**
	 * @see org.happyreaction.rest.controller.base.CrudController#getService()
	 */
	@Override
	public IService<Customer> getService() {
		return customerService;
	}

}
