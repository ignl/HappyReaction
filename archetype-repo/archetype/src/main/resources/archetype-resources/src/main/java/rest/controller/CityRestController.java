#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.controller;

import ${package}.model.City;
import ${package}.rest.controller.base.CrudController;
import ${package}.services.CityService;
import ${package}.services.base.Service;
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
    private CityService cityService;

	/**
	 * {@inheritdoc}
	 */
	@Override
	public Service<City> getService() {
		return cityService;
	}

}
