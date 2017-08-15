package org.happyreaction.services.base;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import org.apache.commons.beanutils.*;
import org.apache.log4j.Logger;
import org.happyreaction.model.base.BaseEntity;
import org.happyreaction.model.base.IEntity;
import org.happyreaction.model.helper.SearchConfig;
import org.happyreaction.repositories.custom.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.beans.Introspector;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Base service that other persistence services can extend. It provides all
 * common crud operations. Also provide default search capabilities which work
 * nicely with composite jsf search components.
 * 
 * @author Ignas
 * 
 * @param <T>
 *            Type of an entity.
 * 
 */
@Transactional(readOnly = true)
public abstract class BaseService<T extends IEntity> implements IService<T>, Serializable {

    private final Logger log = Logger.getLogger(BaseService.class.getName());

    @Autowired
    private IDynamicTypeService dynamicTypeService;

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

    class EnumAwareConvertUtilsBean extends ConvertUtilsBean {
        private final EnumConverter enumConverter = new EnumConverter();
        private final EntityConverter entityConverter = new EntityConverter();

        public Converter lookup(Class clazz) {
            // no specific converter for this class, so it's neither a String, (which has a default converter),
            // nor any known object that has a custom converter for it. It might be an enum !
            if (clazz.isEnum()) {
                return enumConverter;
            } else if (BaseEntity.class.isAssignableFrom(clazz)) {
                return entityConverter;
            }  else {
                return super.lookup(clazz);
            }
        }

        private class EnumConverter implements Converter {
            public Object convert(Class type, Object value) {
                return Enum.valueOf(type, (String) value);
            }
        }

        private class EntityConverter implements Converter {
            @Override
            public <T> T convert(Class<T> type, Object value) {
                if (value instanceof Integer) {
                    return (T)dynamicTypeService.findById(type, ((Integer)value).longValue());
                }
                log.warn("Entity can be converted only from a Integer.");
                return null;
            }
        }
    }

    /**
     * @see org.happyreaction.services.base.IService#update(Long, Map)
     */
    @Override
    @Transactional(readOnly = false)
    public void update(Long id, Map<String, Object> updatedFields) {
        T old = getRepository().findOne(id);
        try {
            Converter dtConverter = new Converter() {
                @Override
                public <T> T convert(Class<T> type, Object value) {
                    if (value instanceof String) {
                        if (type == LocalDate.class) {
                            return (T) LocalDate.parse((String)value, DateTimeFormatter.ISO_DATE_TIME);
                        } else if (type == LocalDateTime.class) {
                            return (T) LocalDateTime.parse((String)value,  DateTimeFormatter.ISO_DATE_TIME);
                        }
                    }

                    return null;
                }
            };

            BeanUtilsBean beanUtilsBean = new BeanUtilsBean(new ConvertUtilsBean() {
                @Override
                public Object convert(String value, Class clazz) {
                    if (clazz.isEnum()){
                        return Enum.valueOf(clazz, value);
                    } else{
                        return super.convert(value, clazz);
                    }
                }
            });


            BeanUtilsBean.setInstance(new BeanUtilsBean(new EnumAwareConvertUtilsBean()));
            ConvertUtils.register(dtConverter, LocalDate.class);
            ConvertUtils.register(dtConverter, LocalDateTime.class);
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
     * @see org.happyreaction.services.base.IService#getEnumConstants(String)
     */
    @Override
    public List<Object> getEnumConstants(String fieldName) {
        try {
            Field field = entityClass.getDeclaredField(fieldName);
            if (field != null && field.getType().isEnum()) {
                return Arrays.asList(field.getType().getEnumConstants());
            }
        } catch (NoSuchFieldException e) {
            throw new IllegalStateException("No field with name '" + fieldName + "' was found");
        }
        throw new IllegalStateException("No field with name '" + fieldName + "' was found");
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
            List<String> filtersToRemove = new ArrayList<>();
            predicate = processNonStandardFilters(filters, filtersToRemove, entityPath);
            removeUsedFilters(filtersToRemove, filters);
            if (!filters.isEmpty()) {
                for (Map.Entry<String, Object> entry : filters.entrySet()) {
                    String fieldName = entry.getKey();
                    String filter = String.valueOf(entry.getValue());
                    Class fieldType = null;
                    try {
                        Field field = entityClass.getDeclaredField(fieldName);
                        fieldType = field.getType();
                    } catch (NoSuchFieldException e) {
                        log.error("Field " + fieldName + " does not exists for an entity " + entityClass + ". Please check your react code if you really provide correct field name.");
                    }
                    if (filter != null && fieldType != null) {

                        // if ranged search (from - to fields)
                        if (fieldName.contains("fromRange-")) {
                            // CHECKSTYLE:OFF
                            String parsedKey = fieldName.substring(10);
                            // CHECKSTYLE:ON
                            if (Number.class.isAssignableFrom(fieldType)) {
//                                NumberPath path = createNumberPath(entityPath, parsedKey, filter);
//                                predicate = and(predicate, path.goe((Number) filter));
                            } else if (Date.class.isAssignableFrom(fieldType)) {
                                DatePath path = entityPath.getDate(parsedKey, Date.class);
//                                predicate = and(predicate, path.goe((Date) filter));
                            }
                        } else if (fieldName.contains("toRange-")) {
                            // CHECKSTYLE:OFF
                            String parsedKey = fieldName.substring(8);
                            // CHECKSTYLE:ON
                            if (Number.class.isAssignableFrom(fieldType)) {
//                                NumberPath path = createNumberPath(entityPath, parsedKey, filter);
//                                predicate = and(predicate, path.loe((Number) filter));
                            } else if (Date.class.isAssignableFrom(fieldType)) {
                                DatePath path = entityPath.getDate(parsedKey, Date.class);
//                                predicate = and(predicate, path.loe((Date) filter));
                            }
                        } else if (fieldName.contains("list-")) {
                            // CHECKSTYLE:OFF
                            // if searching elements from list
                            String parsedKey = fieldName.substring(5);
                            // CHECKSTYLE:ON
                            ListPath path = entityPath.getList(parsedKey, filter.getClass());
                            predicate = and(predicate, path.contains(filter));
                        } else { // if not ranged search
                            if (String.class == fieldType) {
                                StringPath path = entityPath.getString(fieldName);
                                String filterString = (String) filter;
                                predicate = and(predicate, path.startsWithIgnoreCase(filterString));
                            } else if (LocalDate.class.isAssignableFrom(fieldType)) {
                                DatePath path = entityPath.getDate(fieldName, Date.class);
                                predicate = and(predicate, path.eq(LocalDate.parse(filter, DateTimeFormatter.ISO_DATE_TIME)));
                            } else if (LocalDateTime.class.isAssignableFrom(fieldType)) {
                                DatePath path = entityPath.getDate(fieldName, Date.class);
                                predicate = and(predicate, path.eq(LocalDateTime.parse(filter, DateTimeFormatter.ISO_DATE_TIME)));
                            } else if (Number.class.isAssignableFrom(fieldType)) {
                                NumberPath path = entityPath.getNumber(fieldName, fieldType);
                                predicate = and(predicate, path.eq(getNumber(fieldType, filter)));
                            } else if (Boolean.class == fieldType) {
                                BooleanPath path = entityPath.getBoolean(fieldName);
                                predicate = and(predicate, path.eq(Boolean.valueOf(filter)));
                            } else if (Enum.class.isAssignableFrom(fieldType)) {
                                EnumPath path = entityPath.getEnum(fieldName, Enum.class);
                                predicate = and(predicate, path.eq(Enum.valueOf(fieldType, filter.trim().toUpperCase())));
                            } else if (BaseEntity.class.isAssignableFrom(fieldType)) {
                                PathBuilder path = entityPath.get(fieldName + ".id");
                                predicate = and(predicate, path.eq(Long.valueOf(filter)));
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private Number getNumber(Class clazz, String filter) {
        if (BigDecimal.class.isAssignableFrom(clazz)) {
            return new BigDecimal(filter);
        } else if (Long.class.isAssignableFrom(clazz)) {
            return new BigDecimal(filter);
        } else if (Integer.class.isAssignableFrom(clazz)) {
            return new BigDecimal(filter);
        } else if (Double.class.isAssignableFrom(clazz)) {
            return new BigDecimal(filter);
        } else if (Float.class.isAssignableFrom(clazz)) {
            return new BigDecimal(filter);
        } else if (Byte.class.isAssignableFrom(clazz)) {
            return new BigDecimal(filter);
        } else if (Short.class.isAssignableFrom(clazz)) {
            return new BigDecimal(filter);
        } else {
            throw new IllegalStateException("Unknown number type in search filter. Supported type: BigDecimal, Long, Integer, Double, Float, Byte, Short");
        }
    }
}
