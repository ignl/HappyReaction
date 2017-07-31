package org.happyreaction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.happyreaction.model.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * CustomerPerk domain model.
 * 
 * @author Ignas
 * 
 */
@Entity
@Table(name = "CUSTOMER_PERK")
@Getter
@Setter
public class CustomerPerk extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    /** Perk name. */
    @Column(name = "NAME", nullable = false)
    private String name;
    
    /** Perk description. */
    @Column(name = "DESCRIPTION")
    private String description;

}
