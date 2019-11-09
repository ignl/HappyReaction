package org.happyreaction.services.base.converters;

import org.apache.commons.beanutils.Converter;

/**
 *  Generic enum converter that uses {@see Enum$valueOf} method to convert string to enum value.
 *  This approach allow to not use a new converter for each type of enum.
 */
class EnumConverter implements Converter {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object convert(Class type, Object value) {
        return Enum.valueOf(type, (String) value);
    }

}