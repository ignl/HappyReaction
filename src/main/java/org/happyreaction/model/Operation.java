package org.happyreaction.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.happyreaction.model.base.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * Operation domain model.
 * 
 * @author Ignas
 * 
 */
@Entity
@Table(name = "OPERATION")
@Getter
@Setter
public class Operation extends BaseEntity {
    
    /** */
    private static final long serialVersionUID = 1L;

    /** Operation name. */
    @Column(name = "OPERATION_NAME")
    private String operationName;
    
    /** Operation sum. */
    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;
    
    /** Operation type. */
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "OPERATION_TYPE", nullable = false)
    private OperationType operationType;
    
    /** Operation account. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
    
    /** Operation date. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "OPERATION_DATE")
    private Date operationDate;
    
    /** Operation comment. */
    @Column(name = "COMMENT")
    private String comment;

}
