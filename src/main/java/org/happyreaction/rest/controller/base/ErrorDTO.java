package org.happyreaction.rest.controller.base;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO object for returning back error messages for REST WS.
 * 
 * @author Ignas
 *
 */
public class ErrorDTO {

    /** Description. */
    private String description;

    /** Details. */
    private List<String> details = new ArrayList<String>();

    /** Constructor. */
    public ErrorDTO(String description) {
        super();
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    /** Add single detail to details list. */
    public void addDetail(String detail) {
        this.details.add(detail);
    }
    
}
