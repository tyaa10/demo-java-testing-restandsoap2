package org.tyaa.demo.java.testing.restandsoap.exceptions.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvalidDateTypeException extends RuntimeException {
    private String key;
    private String value;
}
