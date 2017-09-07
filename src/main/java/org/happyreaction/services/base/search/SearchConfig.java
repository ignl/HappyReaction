package org.happyreaction.services.base.search;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;

/**
 * This is helper class to keep all information about search request pagination,
 * sorting, filtering, fields to fetch, ordering etc.
 * 
 * @author Ignas
 * 
 */
@Getter
@AllArgsConstructor
public class SearchConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /** First row from which page count is started. */
    private int firstRow;
    
    /** Number of rows per page. */
    private int numberOfRows;

    /** Search filters (key = field name, value = search pattern or value). */
    private Map<String, Object> filters;

    /**
     * Fields that needs to be fetched when selecting (like lists or other
     * entities).
     */
    private List<String> fetchFields;

    /** Field name to sort by. */
    private String sortField;

    /** Sorting direction. Ascending or descending. */
    private Direction ordering;

}
