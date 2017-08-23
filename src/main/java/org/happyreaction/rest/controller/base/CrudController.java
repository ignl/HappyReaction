package org.happyreaction.rest.controller.base;

import java.util.List;
import java.util.Map;

import org.happyreaction.model.base.IEntity;
import org.happyreaction.services.base.search.SearchConfig;
import org.happyreaction.services.base.search.SearchConfigEditor;
import org.happyreaction.services.base.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Abstract base class for CRUD operations. Supports listing entities with pagination,
 * creating new ones, updating and deleting.
 * 
 * @author Ignas
 *
 * @param <T> Entity class.
 */
public abstract class CrudController<T extends IEntity> extends ErrorHandlingController {

	/**
	 * Register property editor for {@link SearchConfig} class.
	 * @param binder WebDataBinder is a DataBinder that binds request parameter to JavaBean objects.
	 */
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
	 * @return All enum values of the field.
	 */
	@RequestMapping(value = "/allEnumValues", produces = "application/json", method = RequestMethod.GET)
	public ResponseEntity<List<Object>> listAllEnumValues(@RequestParam("fieldName") String fieldName) {
		return new ResponseEntity<List<Object>>(getService().getEnumConstants(fieldName), HttpStatus.OK);
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
	 * Find entity by its ID.
	 *
	 * @param id Entity identifier.
	 * @param fetchFields Requested fields to fetch when loading entity from database.
	 * If you need to show some lazy loaded fields - this parameter must be used to fetch them.
	 *
	 * @return Entity with all requested fields fetched and  HttpStatus.OK response if operation is successful.
	 */
	@RequestMapping(value = "/findById/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET)
	public ResponseEntity<T> findById(@PathVariable(value="id") Long id, @RequestParam(value="fetchFields", required=false) List<String> fetchFields) {
		return new ResponseEntity<T>(getService().findById(id, fetchFields), HttpStatus.OK);
	}

	/**
	 * Delete entity by its ID.
	 *
	 * @param id Entity identifier.
	 * @return True if operation is successful.
	 */
	@RequestMapping(value = "/delete/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.DELETE)
	public ResponseEntity delete(@PathVariable("id") Long id) {
		getService().delete(id);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * Create a new entity.
	 *
	 * @return True if operation is successful.
	 */
	@RequestMapping(value = "/add", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public ResponseEntity add(@RequestBody Map<String, Object> newEntityValues) {
		getService().add(newEntityValues);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * Update entity fields.
	 *
	 * @return True if operation is successful.
	 */
	@RequestMapping(value = "/update/{id}", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
	public ResponseEntity update(@PathVariable Long id, @RequestBody Map<String, Object> updatedFields) {
		getService().update(id, updatedFields);
		return new ResponseEntity(HttpStatus.OK);
	}

	/**
	 * @return Concrete implementation of service which is injected in super class.
	 */
	public abstract Service<T> getService();
	
}
