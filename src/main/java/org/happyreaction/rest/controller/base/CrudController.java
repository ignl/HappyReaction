package org.happyreaction.rest.controller.base;

import java.util.List;

import org.happyreaction.model.base.IEntity;
import org.happyreaction.model.helper.SearchConfig;
import org.happyreaction.model.helper.SearchConfigEditor;
import org.happyreaction.services.base.IService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Abstract base class for CRUD operations. Supports listing entities with pagination,
 * creating new ones, updating and deleting.
 * 
 * @author Ignas
 *
 * @param <T> Entity class.
 */
public abstract class CrudController<T extends IEntity> extends ErrorHandlingController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(SearchConfig.class, new SearchConfigEditor());
	}
	
	/**
	 * @return All entities in database.
	 */
	@RequestMapping(value = "/all", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<T>> listAll() {
		return new ResponseEntity<List<T>>(getService().list(), HttpStatus.OK);
	}
	
	/**
	 * @param searchConfig Pagination, sorting and  information.
	 * @return All entities that matches search data.
	 */
	@RequestMapping(value = "/search", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public ResponseEntity<List<T>> search(@RequestParam("searchConfig") SearchConfig searchConfig) {
		return new ResponseEntity<List<T>>(getService().list(searchConfig), HttpStatus.OK);
	}

	/**
	 * @param searchConfig Pagination, sorting and  information.
	 * @return Number of entities that matches search data
	 */
	@RequestMapping(value = "/count", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public ResponseEntity<Long> count(@RequestParam("searchConfig") SearchConfig searchConfig) {
		return new ResponseEntity<Long>(getService().count(searchConfig), HttpStatus.OK);
	}

	/**
	 * Create a new entity.
	 * 
	 * @return True if operation is successful.
	 */
	public boolean add(T entity) {
		getService().add(entity);
		return true;
	}
	
	/**
	 * Update entity fields.
	 * 
	 * @return True if operation is successful.
	 */
	public boolean update(T entity) {
		getService().update(entity);
		return true;
	}
	
	/**
	 * Delete entity by its ID.
	 * 
	 * @param id Entity identifier.
	 * @return True if operation is successful.
	 */
	public boolean delete(Long id) {
		getService().delete(id);
		return true;
	}
	
	/**
	 * Find entity by its ID.
	 * 
	 * @param id Entity identifier.
	 * @return Entity.
	 */
	public T findById(Long id) {
		return getService().findById(id);
	}
	
	/**
	 * Find entity by its ID.
	 * 
	 * @param id Entity identifier.
	 * @param fetchFields Requested fields to fetch when loading entity from database. 
	 * If you need to show some lazy loaded fields - this parameter must be used to fetch them.
	 * 
	 * @return Entity with all requested fields fetched.
	 */
	public T findById(Long id, List<String> fetchFields) {
		return getService().findById(id, fetchFields);
	}

	/**
	 * @return Concrete implementation of service which is injected in super class.
	 */
	public abstract IService<T> getService();
	
}
