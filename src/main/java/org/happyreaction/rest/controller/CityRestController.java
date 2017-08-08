package org.happyreaction.rest.controller;

import org.happyreaction.model.City;
import org.happyreaction.model.Operation;
import org.happyreaction.rest.controller.base.CrudController;
import org.happyreaction.services.ICityService;
import org.happyreaction.services.IOperationService;
import org.happyreaction.services.base.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST Webservices controller for Cities. All supported WS methods are mapped to this controller.
 * 
 * @author Ignas
 * 
 */
@Controller
@RequestMapping("/city")
public class CityRestController extends CrudController<City> {

    /** Injected service. */
    @Autowired
    private ICityService cityService;

	/**
	 * @see CrudController#getService()
	 */
	@Override
	public IService<City> getService() {
		return cityService;
	}

}
