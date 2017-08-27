#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.services.base.converters;

import org.apache.commons.beanutils.Converter;

/**
 *  Generic enum converter that uses {@see Enum${symbol_dollar}valueOf} method to convert string to enum value.
 *  This approach allow to not use a new converter for each type of enum.
 */
public class EnumConverter implements Converter {

    /**
     * {@inheritDoc}
     */
    @Override
    public Object convert(Class type, Object value) {
        return Enum.valueOf(type, (String) value);
    }

}