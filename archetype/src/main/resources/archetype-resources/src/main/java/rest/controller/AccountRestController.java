#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.controller;

import ${package}.model.Account;
import ${package}.rest.controller.base.CrudController;
import ${package}.services.AccountService;
import ${package}.services.base.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST Webservices controller for Accounts. All supported WS methods are mapped to this controller.
 * 
 * @author Ignas
 * 
 */
@Controller
@RequestMapping("/account")
public class AccountRestController extends CrudController<Account> {

    /** Injected service. */
    @Autowired
    private AccountService accountService;


	/**
	 * {@inheritdoc}
	 */
	@Override
	public Service<Account> getService() {
		return accountService;
	}

}
