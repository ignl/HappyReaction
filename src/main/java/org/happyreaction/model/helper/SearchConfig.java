package org.happyreaction.model.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort.Direction;

import lombok.Getter;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This is helper class to keep all information about search request pagination,
 * sorting, filtering, fields to fetch, ordering etc.
 * 
 * @author Ignas
 * 
 */
@Getter
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

    /**
     * Constructor with all fields.
     */
    public SearchConfig(int firstRow, int numberOfRows, Map<String, Object> filters,
            List<String> fetchFields, String sortField, Direction ordering) {
        this.firstRow = firstRow;
        this.numberOfRows = numberOfRows;
        this.filters = filters != null ? filters : new HashMap<>();
        this.fetchFields = fetchFields != null ? fetchFields : new ArrayList<>();
        this.sortField = sortField;
        this.ordering = ordering;
    }


    public boolean isSorted() {
        return ordering != null && sortField != null && sortField.trim().length() != 0;
    }

}
