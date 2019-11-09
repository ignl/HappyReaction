package org.happyreaction.rest.controller;

import org.happyreaction.model.Customer;
import org.happyreaction.rest.controller.base.CrudController;
import org.happyreaction.services.CustomerService;
import org.happyreaction.services.base.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST Webservices controller for Customers. All supported WS methods are mapped to this controller.
 */
@Controller
@RequestMapping("/customer")
public class CustomerRestController extends CrudController<Customer> {

    @Autowired
    private CustomerService customerService;

	/**
	 * {@inheritdoc}
	 */
	@Override
	public Service<Customer> getService() {
		return customerService;
	}

}
