package org.happyreaction.model;

import lombok.Getter;

/**
 * Enum for all operation types.
 */
@Getter
public enum OperationType {
    
    CREDIT("Credit"),
    DEBIT("Debit");
    
    private final String label;
    
    OperationType(String label) {
        this.label = label;
    }
    
}
