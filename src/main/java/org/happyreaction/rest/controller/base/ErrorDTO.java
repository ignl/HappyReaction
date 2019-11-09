package org.happyreaction.rest.controller.base;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO object for returning back error messages for REST WS.
 */
@RequiredArgsConstructor
@Getter
@Setter
public class ErrorDTO {

    @NonNull
    private final String description;

    private List<String> details = new ArrayList<>();

    public void addDetail(String detail) {
        this.details.add(detail);
    }
    
}
