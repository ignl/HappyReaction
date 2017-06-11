package org.happyreaction.model.base;

/**
 * Interface for identifiableEnum. This is used when enum is mapped in model as
 * an integer but we do not want to use ordinal (for example if order of enum values might change).
 * 
 * @author Ignas
 * 
 */
public interface IdentifiableEnum {

    /**
     * Enum id that is saved in database.
     */
    Integer getId();
    
    /**
     * Enum label.
     */
    String getLabel();

}
