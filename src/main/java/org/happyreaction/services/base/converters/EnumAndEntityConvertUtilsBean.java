package org.happyreaction.services.base.converters;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.happyreaction.model.base.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Private class used to convert to Enums and Entities when String or integer is received from front end application.
 * It has two internal converters EnumConverter and EntityConverter.
 * EnumConverter simply takes String input and return Enum.valueOf that string.
 * EntityConverter takes entity ID as an input and loads the entity from the database.
 */
@Component
public class EnumAndEntityConvertUtilsBean extends ConvertUtilsBean {

    /** Generic enum converter that works for any enum. */
    private final EnumConverter enumConverter = new EnumConverter();

    /**
     * Entity converter. Autowired by Spring because it also contains {@link org.happyreaction.services.base.DynamicTypeService}
     * bean which must also be injected.
     */
    @Autowired
    private EntityConverter entityConverter;

    /**
     * Set BeanUtilsBean for generic Entity and Enum converters and register Date converters right after this bean is constructed.
     * After it is done commons-beanutils will work as needed globally.
     */
    @PostConstruct
    public void registerConverters() {
        BeanUtilsBean.setInstance(new BeanUtilsBean(new EnumAndEntityConvertUtilsBean()));
        ConvertUtils.register(new DateTimeConverter(), LocalDate.class);
        ConvertUtils.register(new DateTimeConverter(), LocalDateTime.class);
    }

    /**
     * {@inheritDoc}
     *
     * Because by default ConvertUtilsBean has converters for any concrete class we need to modify lookup() method
     * so that all classes that extend {@link BaseEntity} or are enums would receive same generic converters.
     * That way we don't need to write a new converter for each different enum or new entity.
     */
    @Override
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

}