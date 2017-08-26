#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model;

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
