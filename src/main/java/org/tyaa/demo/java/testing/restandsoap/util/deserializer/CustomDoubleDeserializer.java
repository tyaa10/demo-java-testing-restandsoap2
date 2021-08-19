package org.tyaa.demo.java.testing.restandsoap.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.tyaa.demo.java.testing.restandsoap.exceptions.type.InvalidTypeException;

import java.io.IOException;

public class CustomDoubleDeserializer extends StdDeserializer<Double> {

    public CustomDoubleDeserializer() {
        this(null);
    }

    public CustomDoubleDeserializer(Class t) {
        super(t);
    }

    @Override
    public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Double val;

        try {
            val = jsonParser.getDoubleValue();
        } catch (NumberFormatException | IOException e) {
            throw new InvalidTypeException(jsonParser.getCurrentName(), jsonParser.getText(), Double.class);
        }
        return val;
    }
}
