package com.example.ast;

public class AstException extends RuntimeException {
    public AstException(String message) {
        super(message);
    }

    public AstException(String message, Throwable cause) {
        super(message, cause);
    }
}
