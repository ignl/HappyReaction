#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.controller;

import ${package}.model.Customer;
import ${package}.rest.controller.base.CrudController;
import ${package}.services.CustomerService;
import ${package}.services.base.Service;
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
    private CustomerService customerService;

	/**
	 * {@inheritdoc}
	 */
	@Override
	public Service<Customer> getService() {
		return customerService;
	}

}
