package org.happyreaction.rest.controller;

import org.happyreaction.model.Operation;
import org.happyreaction.rest.controller.base.CrudController;
import org.happyreaction.services.IOperationService;
import org.happyreaction.services.base.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST Webservices controller for Operations. All supported WS methods are mapped to this controller.
 * 
 * @author Ignas
 * 
 */
@Controller
@RequestMapping("/operation")
public class OperationRestController extends CrudController<Operation> {

    /** Injected service. */
    @Autowired
    private IOperationService operationService;

	/**
	 * @see org.happyreaction.rest.controller.base.CrudController#getService()
	 */
	@Override
	public IService<Operation> getService() {
		return operationService;
	}

}
