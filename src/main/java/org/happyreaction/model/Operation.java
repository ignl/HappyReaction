package org.happyreaction.model;

import lombok.Getter;
import lombok.Setter;
import org.happyreaction.model.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    @Column(name = "OPERATION_DATE")
    private LocalDateTime operationDate;
    
    /** Operation comment. */
    @Column(name = "COMMENT")
    private String comment;

}
