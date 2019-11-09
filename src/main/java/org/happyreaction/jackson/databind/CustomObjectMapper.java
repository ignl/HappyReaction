package org.happyreaction.jackson.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * Project specific Jackson {@link ObjectMapper}.
 */
public class CustomObjectMapper extends ObjectMapper {

    /**
     * Constructor with additional configuration needed for Java8 {@link java.time.LocalDate} and
     * {@link java.time.LocalDateTime} to work with Jackson mapper.
     */
    public CustomObjectMapper() {
        registerModule(new JavaTimeModule());
        registerModule(new Hibernate5Module());
        configure(WRITE_DATES_AS_TIMESTAMPS, false);
    }

}