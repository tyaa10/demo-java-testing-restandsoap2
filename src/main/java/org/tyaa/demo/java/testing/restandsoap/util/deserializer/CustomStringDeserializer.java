package org.tyaa.demo.java.testing.restandsoap.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.tyaa.demo.java.testing.restandsoap.exceptions.type.InvalidTypeException;

import java.io.IOException;

public class CustomStringDeserializer extends StdDeserializer<String> {

    public CustomStringDeserializer() {
        this(null);
    }

    public CustomStringDeserializer(Class t) {
        super(t);
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.getCurrentToken().isNumeric()
                || jsonParser.getCurrentToken().isBoolean()
                || jsonParser.getCurrentToken().isStructStart()) {
            throw new InvalidTypeException(jsonParser.getCurrentName(), jsonParser.getText(), String.class);
        }
        return jsonParser.getValueAsString();
    }
}
