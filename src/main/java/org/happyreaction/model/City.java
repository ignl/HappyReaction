package org.happyreaction.model;

import lombok.Getter;
import lombok.Setter;
import org.happyreaction.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * City domain model.
 * 
 * @author Ignas
 * 
 */
@Entity
@Table(name = "CITY")
@Getter
@Setter
public class City extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    /** City name. */
    private String name;
    
    /** Country of the city. */
    private String country;

}
