package org.happyreaction.repositories.custom;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.data.repository.support.PageableExecutionUtils.TotalSupplier;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

/**
 * Custom spring data repository which extends {@link QueryDslJpaRepository} and adds additional methods. It is capable
 * to have some methods accept list of strings which contains what names have to be fetched.
 * 
 * @author Ignas
 * 
 * @param <T>
 *            Entity type.
 * @param <ID>
 *            Entity ID type.
 */
@NoRepositoryBean
public class GenericRepositoryImpl<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID> implements
        GenericRepository<T, ID>, Serializable {

    /**
     * Class version id for serialization. After a change to serialized field this number should be changed so it would
     * be clear its different class version.
     */
    private static final long serialVersionUID = 1L;

    /** */
    private final JpaEntityInformation<T, ?> entityInformation;

    /** */
    private final EntityManager em;

    /** */
    private final EntityPath<T> path;

    /** */
    private final PathBuilder<T> builder;

    /** */
    private final Querydsl querydsl;

    /**
     * Creates a new {@link QueryDslJpaRepository} to manage objects of the given {@link JpaEntityInformation}.
     * 
     * @param entityInformation
     * @param entityManager
     */
   
    public GenericRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager, // NOPMD
            Class<?> springDataRepositoryInterface) {
        super(entityInformation, entityManager);

        this.em = entityManager;
        this.entityInformation = entityInformation;
        this.path = SimpleEntityPathResolver.INSTANCE.createPath(entityInformation.getJavaType());
        this.builder = new PathBuilder<T>(path.getType(), path.getMetadata());
        this.querydsl = new Querydsl(entityManager, builder);
    }

    /**
     * @see com.test.customrepository.GenericRepository#findOne(java.io.Serializable, java.util.List)
     */
    @Override
    public final T findOne(ID id, List<String> fetchFields) {
        if (fetchFields == null || fetchFields.isEmpty()) {
            return super.findOne(id);
        }
        // TODO rewrite
        StringBuilder queryString = new StringBuilder("from " + entityInformation.getJavaType().getSimpleName() + " a");
        if (!fetchFields.isEmpty()) {
            for (String fetchField : fetchFields) {
                queryString.append(" left join fetch a." + fetchField);
            }
        }
        queryString.append(" where a.id = :id");
        Query query = em.createQuery(queryString.toString());
        query.setParameter("id", id);

        @SuppressWarnings("unchecked")
        List<T> list = query.getResultList();

        return list.size() > 0 ? (T) list.get(0) : null;
    }

    /**
     * @see com.test.customrepository.GenericRepository#findAll(com.mysema.query.types.Predicate, org.springframework.data.domain.Pageable, java.util.List)
     */
    @Override
    public final Page<T> findAll(Predicate predicate, Pageable pageable, List<String> fetchFields) {
        if (fetchFields == null || fetchFields.isEmpty()) {
            return super.findAll(predicate, pageable);
        }
        
        final JPQLQuery<?> countQuery = createCountQuery(predicate);
		@SuppressWarnings("unchecked")
		JPQLQuery<T> query = querydsl.applyPagination(pageable, createQuery(predicate).select(path));
		for (String fetchField : fetchFields) {
			query.leftJoin(builder.get(fetchField)).fetch();
        }

		return PageableExecutionUtils.getPage(query.fetch(), pageable, new TotalSupplier() {

			@Override
			public long get() {
				return countQuery.fetchCount();
			}
		});
    }

    /**
     * Same as in supper just uses current class private fields.
     * 
     * @see org.springframework.data.jpa.repository.support.QueryDslJpaRepository#createQuery(com.mysema.query.types.Predicate[])
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    protected final JPQLQuery createQuery(Predicate... predicate) {
        return querydsl.createQuery(path).where(predicate);
    }

}