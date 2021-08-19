package org.tyaa.demo.java.testing.restandsoap.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;

public class CustomDateSerializer extends StdSerializer<LocalDate> {
    public CustomDateSerializer() {
        this(null);
    }

    public CustomDateSerializer(Class<LocalDate> d) {
        super(d);
    }

    @Override
    public void serialize(LocalDate date, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeString(date.toString());
    }
}
