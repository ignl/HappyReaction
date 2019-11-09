package org.happyreaction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.happyreaction.model.base.BaseEntity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

/**
 * Customer domain model.
 */
@Entity
@Table(name = "CUSTOMER")
@Getter
@Setter
public class Customer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private static final int MAX_PHONE_NUMBER_LENGTH = 10;

    private static final int MIN_PHONE_NUMBER_LENGTH = 7;

    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> accounts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CITY_ID")
    private City city;

    @Column(name = "ADDRESS")
    private String address;

    @Email
    @Column(name = "EMAIL")
    private String email;

    @Length(min = MIN_PHONE_NUMBER_LENGTH, max = MAX_PHONE_NUMBER_LENGTH)
    @Column(name = "PHONE")
    private String phone;

    @Column(name = "AGE")
    private Integer age;

}