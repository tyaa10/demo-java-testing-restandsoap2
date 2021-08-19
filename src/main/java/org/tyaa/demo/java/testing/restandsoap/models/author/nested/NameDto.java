package org.tyaa.demo.java.testing.restandsoap.models.author.nested;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.tyaa.demo.java.testing.restandsoap.util.deserializer.CustomStringDeserializer;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class NameDto {

    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String first;

    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String second;
}
