package com.example.ast;

import lombok.Getter;

@Getter
public class StringValue extends AstData {
    private final String value;

    public StringValue(String value) {
        super(Priorities.MAX_PRIORITY);
        this.value = value;
    }

    public String toString() {
        return value;
    }
}
