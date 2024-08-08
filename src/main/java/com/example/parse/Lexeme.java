package com.example.parse;

public interface Lexeme {
    enum LexemeType {
        EOF,
        INT_LITERAL,
        SYMBOL
    }

    LexemeType getType();
}
