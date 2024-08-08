package com.example.ast;

public interface Operator {
    enum OperatorType {
        PLUS, MINUS, MULTIPLY, DIVIDE
    }

    OperatorType getOperatorType();
}
