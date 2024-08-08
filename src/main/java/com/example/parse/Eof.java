package com.example.parse;

public class Eof implements Lexeme {
    @Override
    public LexemeType getType() {
        return LexemeType.EOF;
    }
}
