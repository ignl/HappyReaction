package org.happyreaction.services.base.converters;

import org.apache.commons.beanutils.Converter;
import org.happyreaction.services.base.DynamicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Entity converter which receives integer and entity type and loads that entity from database.
 */
@Component
public class EntityConverter implements Converter {

    /** Dynamic type service is able to load any type of entity by its ID. */
    @Autowired
    private DynamicTypeService dynamicTypeService;

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T convert(Class<T> type, Object value) {
        if (value instanceof Integer) {
            return (T)dynamicTypeService.findById(type, ((Integer)value).longValue());
        }
        throw new IllegalStateException("Incorect usage of EntityConverter. It accepts only Integer values.");
    }
}
