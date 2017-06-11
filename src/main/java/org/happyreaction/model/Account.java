package org.happyreaction.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.happyreaction.model.base.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * Account domain model.
 * 
 * @author Ignas
 * 
 */
@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
public class Account extends BaseEntity {

    /** */
    private static final long serialVersionUID = 1L;

    /** Customer. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonIgnore
    private Customer customer;

    /** Account number. */
    @NotNull
    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private String accountNumber;

    /** Is account active. */
    @Column(name = "ACCOUNT_ACTIVE")
    private Boolean active;

    /** Account operations history. */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Operation> operations;

    // TODO java 8 date
    /** Account opening date. */
    @Temporal(TemporalType.DATE)
    @Column(name = "OPENING_DATE")
    private Date openingDate;

}
