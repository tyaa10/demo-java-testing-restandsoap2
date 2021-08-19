package org.tyaa.demo.java.testing.restandsoap.models.author.nested;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.tyaa.demo.java.testing.restandsoap.util.deserializer.CustomDateDeserializer;
import org.tyaa.demo.java.testing.restandsoap.util.deserializer.CustomStringDeserializer;
import org.tyaa.demo.java.testing.restandsoap.util.serializer.CustomDateSerializer;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class BirthDto {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private LocalDate date;

    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String country = "";

    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String city = "";
}
