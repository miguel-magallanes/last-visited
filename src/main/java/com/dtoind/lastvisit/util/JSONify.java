package com.dtoind.lastvisit.util;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.dtoind.lastvisit.model.Link;

/**
 * This class provides utility methods for converting Java objects to JSON strings.
 */
public class JSONify {

    private static Logger logger = LogManager.getLogger(JSONify.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());

        SimpleModule module = new SimpleModule();
        module.addSerializer(Link.class, new JsonSerializer<Link>() {
            @Override
            public void serialize(Link link, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                    throws IOException {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("id", link.getId());
                jsonGenerator.writeObjectField("category", link.getCategory().getName());
                jsonGenerator.writeStringField("name", link.getName());
                jsonGenerator.writeStringField("url", link.getUrl());
                jsonGenerator.writeObjectField("dateTime", link.getFormattedDateTime());
                jsonGenerator.writeNumberField("numVisits", link.getNumVisits());
                jsonGenerator.writeEndObject();
            }
        });
        objectMapper.registerModule(module);
    }

    /**
     * Converts the given object to its JSON representation.
     *
     * @param obj the object to be converted
     * @return the JSON string representation of the object
     * @throws RuntimeException if an error occurs during the conversion
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("Failed to convert object to JSON", e);
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

}
