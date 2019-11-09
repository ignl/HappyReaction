package org.happyreaction.repositories.custom;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.querydsl.core.types.Predicate;

/**
 * Repository which contains all JPARepository and QueryDslPredicateExecutor
 * methods and also some on its own methods to add missing features of spring
 * data.
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface GenericRepository<T, ID extends Serializable> extends JpaRepository<T, ID>,
        QueryDslPredicateExecutor<T> {
    
    /**
     * Additional feature for spring data findAll() method which allows to pass list of fields that needs to be fetched.
     */
    Page<T> findAll(Predicate predicate, Pageable pageable, List<String> fetchFields);
    
    /**
     * Additional feature for spring data findOne() method which allows to pass list of fields that needs to be fetched.
     */
    T findOne(ID id, List<String> fetchFields);
    
}