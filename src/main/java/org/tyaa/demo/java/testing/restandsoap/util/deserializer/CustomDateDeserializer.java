package org.tyaa.demo.java.testing.restandsoap.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.tyaa.demo.java.testing.restandsoap.exceptions.type.InvalidDateTypeException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomDateDeserializer extends StdDeserializer<LocalDate> {
    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(Class t) {
        super(t);
    }

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext dc) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parsed;
        try {
            parsed = LocalDate.parse(jp.getValueAsString(), formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateTypeException(jp.currentName(), jp.getText());
        }

        return parsed;
    }
}
