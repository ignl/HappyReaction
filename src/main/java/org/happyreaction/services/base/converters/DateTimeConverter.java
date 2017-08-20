package org.happyreaction.services.base.converters;

import org.apache.commons.beanutils.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *  commons-beanutils Converter that is used to convert String to {@link LocalDate} and {@link LocalDateTime}.
 *  From React app dates are received as strings and needs to be converted and set from map onto an entity bean
 *  and this is done by commons-beanutils with this converter.
 */
public class DateTimeConverter implements Converter {

    /**
     *  {@inheritdoc}
     *
     *  Converts from string to either LocalDate or LocalDateTime.
     */
    @Override
    public <T> T convert(Class<T> type, Object value) {
        if (value instanceof String) {
            if (type == LocalDate.class) {
                return (T) LocalDate.parse((String)value, DateTimeFormatter.ISO_DATE_TIME);
            } else if (type == LocalDateTime.class) {
                return (T) LocalDateTime.parse((String)value,  DateTimeFormatter.ISO_DATE_TIME);
            }
        }
        throw new IllegalStateException("Date converter accepts only String input.");
    }
}
