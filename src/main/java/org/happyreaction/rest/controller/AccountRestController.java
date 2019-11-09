package org.happyreaction.rest.controller;

import org.happyreaction.model.Account;
import org.happyreaction.rest.controller.base.CrudController;
import org.happyreaction.services.AccountService;
import org.happyreaction.services.base.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST Webservices controller for Accounts. All supported WS methods are mapped to this controller.
 */
@Controller
@RequestMapping("/account")
public class AccountRestController extends CrudController<Account> {

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
