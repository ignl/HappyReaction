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
    CREDIT(1, "operation.credit"), 
    /** Debit operation. */
    DEBIT(2, "operation.debit");
    
    /** Enum id. */
    private Integer id;
    
    /** Enum label. */
    private String label;
    
    /**
     * Constructor.
     */
    private OperationType(Integer id, String label) {
        this.id = id;
        this.label = label;
    }
    
}
