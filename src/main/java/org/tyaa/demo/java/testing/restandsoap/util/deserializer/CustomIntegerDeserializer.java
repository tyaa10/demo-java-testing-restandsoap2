package org.tyaa.demo.java.testing.restandsoap.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.tyaa.demo.java.testing.restandsoap.exceptions.type.InvalidTypeException;

import java.io.IOException;

public class CustomIntegerDeserializer extends StdDeserializer<Integer> {

    public CustomIntegerDeserializer() {
        this(null);
    }

    public CustomIntegerDeserializer(Class t) {
        super(t);
    }

    @Override
    public Integer deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Integer val;

        try {
            val = jsonParser.getIntValue();
        } catch (NumberFormatException | IOException e) {
            throw new InvalidTypeException(jsonParser.getCurrentName(), jsonParser.getText(), Integer.class);
        }
        return val;
    }
}
