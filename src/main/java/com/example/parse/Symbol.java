package com.example.parse;

import lombok.Value;

@Value
public class Symbol implements Lexeme {
    String value;
    @Override
    public LexemeType getType() {
        return LexemeType.SYMBOL;
    }
}
