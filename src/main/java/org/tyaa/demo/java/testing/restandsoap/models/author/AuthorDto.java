package org.tyaa.demo.java.testing.restandsoap.models.author;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import org.tyaa.demo.java.testing.restandsoap.models.author.nested.BirthDto;
import org.tyaa.demo.java.testing.restandsoap.models.author.nested.NameDto;
import org.tyaa.demo.java.testing.restandsoap.util.deserializer.CustomLongDeserializer;
import org.tyaa.demo.java.testing.restandsoap.util.deserializer.CustomStringDeserializer;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class AuthorDto {

    @JsonDeserialize(using = CustomLongDeserializer.class)
    private Long authorId;

    private NameDto authorName;

    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String nationality = "";

    private BirthDto birth = new BirthDto();

    @JsonDeserialize(using = CustomStringDeserializer.class)
    private String authorDescription = "";
}
