package org.happyreaction.rest.controller;

import org.happyreaction.model.Account;
import org.happyreaction.rest.controller.base.CrudController;
import org.happyreaction.services.IAccountService;
import org.happyreaction.services.base.IService;
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
    private IAccountService accountService;


	/**
	 * @see org.happyreaction.rest.controller.base.CrudController#getService()
	 */
	@Override
	public IService<Account> getService() {
		return accountService;
	}

}
