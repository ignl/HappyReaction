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
 */
@Entity
@Table(name = "OPERATION")
@Getter
@Setter
public class Operation extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @Column(name = "OPERATION_NAME")
    private String operationName;
    
    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;
    
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "OPERATION_TYPE", nullable = false)
    private OperationType operationType;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
    
    @Column(name = "OPERATION_DATE")
    private LocalDateTime operationDate;
    
    @Column(name = "COMMENT")
    private String comment;

}
