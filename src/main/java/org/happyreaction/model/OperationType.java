package org.happyreaction.model;

import lombok.Getter;

/**
 * Enum for all operation types.
 * 
 * @author Ignas
 * 
 */
@Getter
public enum OperationType {
    
    /** Credit operation. */
    CREDIT("Credit"),
    /** Debit operation. */
    DEBIT("Debit");
    
    /** Enum label. */
    private String label;
    
    /**
     * Constructor.
     */
    private OperationType(String label) {
        this.label = label;
    }
    
}
