#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.controller;

import ${package}.model.Operation;
import ${package}.rest.controller.base.CrudController;
import ${package}.services.OperationService;
import ${package}.services.base.Service;
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
    private OperationService operationService;

	/**
	 * {@inheritdoc}
	 */
	@Override
	public Service<Operation> getService() {
		return operationService;
	}

}
