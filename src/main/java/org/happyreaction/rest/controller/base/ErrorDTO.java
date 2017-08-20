package org.happyreaction.rest.controller.base;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO object for returning back error messages for REST WS.
 * 
 * @author Ignas
 *
 */
@RequiredArgsConstructor
@Getter
@Setter
public class ErrorDTO {

    /** Description. */
    @NonNull
    private final String description;

    /** Details. */
    private List<String> details = new ArrayList<String>();

    /** Add single detail to details list. */
    public void addDetail(String detail) {
        this.details.add(detail);
    }
    
}
