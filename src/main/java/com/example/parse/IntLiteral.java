package com.example.parse;


import lombok.Value;

@Value
public class IntLiteral implements Lexeme {
    int value;

    @Override
    public LexemeType getType() {
        return LexemeType.INT_LITERAL;
    }
}
