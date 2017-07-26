package org.happyreaction.services.base;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.happyreaction.model.base.BaseEntity;
import org.happyreaction.model.base.IEntity;
import org.happyreaction.model.helper.SearchConfig;
import org.happyreaction.repositories.custom.GenericRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.beans.Introspector;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Base service that other persistence services can extend. It provides all
 * common crud operations. Also provide default search capabilities which work
 * nicely with composite jsf search components.
 * 
 * @author Ignas
 * 
 * @param <T>
 *            Type of entity.
 * 
 */
@Transactional(readOnly = true)
public abstract class BaseService<T extends IEntity> implements IService<T>, Serializable {

    private final Logger log = Logger.getLogger(BaseService.class.getName());

    /**
     * Class version id for serialization. After a change to serialized field
     * this number should be changed so it would be clear its different class
     * version.
     */
    private static final long serialVersionUID = 1L;

    // CHECKSTYLE:OFF
    /** Entity class of service. */
    protected final Class<? extends IEntity> entityClass;

    /** JPA entity manager. */
    @PersistenceContext
    protected EntityManager em;

    // CHECKSTYLE:ON

    /**
     * Default constructor. Loads entity class from super service information.
     * It is used
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public BaseService() {
        Class clazz = getClass();
        while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
            clazz = clazz.getSuperclass();
        }
        Object o = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];

        if (o instanceof TypeVariable) {
            this.entityClass = (Class<T>) ((TypeVariable) o).getBounds()[0];
        } else {
            this.entityClass = (Class<T>) o;
        }
    }

    /**
     * Repository object provided by spring data which makes querying and
     * operating on entities much easier.
     */
    protected abstract JpaRepository<T, Long> getRepository();

    /**
     * @see org.happyreaction.services.base.IService#add(IEntity)
     */
    @Override
    @Transactional(readOnly = false)
    public void add(T entity) {
        getRepository().save(entity);
    }

    /**
     * @see org.happyreaction.services.base.IService#update(Long, Map)
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Long id, Map<String, Object> updatedFields) {
        T old = getRepository().findOne(id);
        try {
            BeanUtils.copyProperties(old, updatedFields);
            getRepository().save(old);
        } catch (IllegalAccessException e) {
            log.error("Error while updating!", e);
        } catch (InvocationTargetException e) {
            log.error("Error while updating!", e);
        }
    }

    /**
     * @see org.happyreaction.services.base.IService#delete(Long)
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(T entity) {
        getRepository().delete(entity);
    }

    /**
     * @see org.happyreaction.services.base.IService#delete(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        getRepository().delete(id);
    }

    /**
     * @see org.happyreaction.services.base.IService#deleteMany(java.lang.Iterable)
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteMany(Iterable<Long> ids) {
        for (Long id : ids) {
        	// TODO efficient implementation
            getRepository().delete(id);
        }
    }

    /**
     * @see org.happyreaction.services.base.IService#findById(java.lang.Long)
     */
    @Override
    public T findById(Long id) {
        return getRepository().findOne(id);
    }

    /**
     * @see org.happyreaction.services.base.IService#findById(java.lang.Long,
     *      java.util.List)
     */
    @Override
    public T findById(Long id, List<String> fetchFields) {
        return ((GenericRepository<T, Long>) getRepository()).findOne(id, fetchFields);
    }

    /**
     * @see org.happyreaction.services.base.IService#list()
     */
    @Override
    public List<T> list() {
        return getRepository().findAll();
    }

    /**
     * @see org.happyreaction.services.base.IService#count()
     */
    @Override
    public long count() {
        return getRepository().count();
    }

    /**
     * @see org.happyreaction.services.base.IService#list(SearchConfig)
     */
    @Override
    public List<T> list(final SearchConfig config) {
        Predicate predicate = getPredicate(config);
        Pageable pageable = new PageRequest(config.getFirstRow() / config.getNumberOfRows(), config.getNumberOfRows(),
                config.getSortField() != null ? new Sort(config.getOrdering(), config.getSortField()) : null);
        return ((GenericRepository<T, Long>) getRepository()).findAll(predicate, pageable, config.getFetchFields()).getContent();
    }

    /**
     * @see org.happyreaction.services.base.IService#count(SearchConfig)
     */
    @Override
    public long count(SearchConfig config) {
        Predicate predicate = getPredicate(config);
        return ((GenericRepository<T, Long>) getRepository()).count(predicate);
    }

    /**
     * Creates a Predicate from list of BooleanExpression predicates which
     * represents all search filters.
     * 
     * @param config
     *            PaginationConfiguration data holding object
     * @return query to filter entities according pagination configuration data.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Predicate getPredicate(SearchConfig config) {

        PathBuilder<T> entityPath = new PathBuilder(entityClass, getAliasName(entityClass));
        BooleanExpression predicate = null;

        Map<String, Object> filters = config.getFilters();
        if (filters != null) {
        	// first we process nonstandard filters
            List<String> filtersToRemove = new ArrayList<String>();
            predicate = processNonStandardFilters(filters, filtersToRemove, entityPath);
            removeUsedFilters(filtersToRemove, filters);
            if (!filters.isEmpty()) {
                for (Map.Entry<String, Object> entry : filters.entrySet()) {
                    String key = entry.getKey();
                    Object filter = entry.getValue();
                    if (filter != null) {

                        // if ranged search (from - to fields)
                        if (key.contains("fromRange-")) {
                            // CHECKSTYLE:OFF
                            String parsedKey = key.substring(10);
                            // CHECKSTYLE:ON
                            if (filter instanceof Number) {
                                NumberPath path = createNumberPath(entityPath, parsedKey, filter);
                                predicate = and(predicate, path.goe((Number) filter));
                            } else if (filter instanceof Date) {
                                DatePath path = entityPath.getDate(parsedKey, Date.class);
                                predicate = and(predicate, path.goe((Date) filter));
                            }
                        } else if (key.contains("toRange-")) {
                            // CHECKSTYLE:OFF
                            String parsedKey = key.substring(8);
                            // CHECKSTYLE:ON
                            if (filter instanceof Number) {
                                NumberPath path = createNumberPath(entityPath, parsedKey, filter);
                                predicate = and(predicate, path.loe((Number) filter));
                            } else if (filter instanceof Date) {
                                DatePath path = entityPath.getDate(parsedKey, Date.class);
                                predicate = and(predicate, path.loe((Date) filter));
                            }
                        } else if (key.contains("list-")) {
                            // CHECKSTYLE:OFF
                            // if searching elements from list
                            String parsedKey = key.substring(5);
                            // CHECKSTYLE:ON
                            ListPath path = entityPath.getList(parsedKey, filter.getClass());
                            predicate = and(predicate, path.contains(filter));
                        } else { // if not ranged search
                            if (filter instanceof String) {
                                StringPath path = entityPath.getString(key);
                                String filterString = (String) filter;
                                predicate = and(predicate, path.startsWithIgnoreCase(filterString));
                            } else if (filter instanceof Date) {
                                DatePath path = entityPath.getDate(key, Date.class);
                                predicate = and(predicate, path.eq(filter));
                            } else if (filter instanceof Number) {
                                NumberPath path = createNumberPath(entityPath, key, filter);
                                predicate = and(predicate, path.eq(filter));
                            } else if (filter instanceof Boolean) {
                                BooleanPath path = entityPath.getBoolean(key);
                                predicate = and(predicate, path.eq((Boolean) filter));
                            } else if (filter instanceof Enum) {
                                EnumPath path = entityPath.getEnum(key, Enum.class);
                                predicate = and(predicate, path.eq(filter));
                            } else if (BaseEntity.class.isAssignableFrom(filter.getClass())) {
                                PathBuilder path = entityPath.get(key);
                                predicate = and(predicate, path.eq(filter));
                            }
                        }

                    }
                }
            }
        }
        return predicate;
    }

    /**
     * This method groups some filters to one. This might be needed when several filters are dependent on each other,
     * for example when we have several text fields and we want all of them to participate in search and we need OR
     * functionality between them. Do not forget to remove filters so getPredicate() method would not try to process them again.
     * 
     * @return processed filters keys.
     */
    protected BooleanExpression processNonStandardFilters(Map<String, Object> filters, List<String> filtersToRemove,
            @SuppressWarnings("rawtypes") PathBuilder pathBuilder) {
        return null;
    }

    /**
     * Remove filters from further processing.
     */
    private Map<String, Object> removeUsedFilters(List<String> filtersToRemove, Map<String, Object> filtersMap) {
        for (String key : filtersToRemove) {
            filtersMap.remove(key);
        }
        return filtersMap;
    }

    /**
     * Class name in lower case as alias is used for spring data.
     */
    private String getAliasName(@SuppressWarnings("rawtypes") Class clazz) {
        return Introspector.decapitalize(clazz.getSimpleName());
    }

    /**
     * Join all predicates with and clause.
     */
    private BooleanExpression and(BooleanExpression old, BooleanExpression newPredidcate) {
        if (old != null) {
            return old.and(newPredidcate);
        } else {
            return newPredidcate;
        }
    }

    /**
     * If filter is number its required to know its concrete class so this
     * private helper method creates and returns predicate based on concrete
     * class.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private NumberPath createNumberPath(PathBuilder entityPath, String key, Object filter) {
        if (filter instanceof BigDecimal) {
            return entityPath.getNumber(key, BigDecimal.class);
        } else if (filter instanceof Long) {
            return entityPath.getNumber(key, Long.class);
        } else if (filter instanceof Integer) {
            return entityPath.getNumber(key, Integer.class);
        } else if (filter instanceof Double) {
            return entityPath.getNumber(key, Double.class);
        } else if (filter instanceof Float) {
            return entityPath.getNumber(key, Float.class);
        } else if (filter instanceof Byte) {
            return entityPath.getNumber(key, Byte.class);
        } else if (filter instanceof Short) {
            return entityPath.getNumber(key, Short.class);
        } else {
            throw new IllegalStateException("Unknown number type in search filter. Supported type: BigDecimal, Long, Integer, Double, Float, Byte, Short");
        }
    }
}
