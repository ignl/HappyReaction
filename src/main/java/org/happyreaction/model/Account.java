package org.happyreaction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.happyreaction.model.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

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

    private static final long serialVersionUID = 1L;

    /** Customer. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
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

    /** Account opening date. */
    @Column(name = "OPENING_DATE")
    private LocalDate openingDate;

}
