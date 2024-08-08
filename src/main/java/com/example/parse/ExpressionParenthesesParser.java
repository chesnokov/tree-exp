package com.example.parse;

import com.example.ast.AstData;
import com.example.tree.Node;

import java.util.Deque;

public class ExpressionParenthesesParser implements Parser {

    private Node<AstData> value;

    @Override
    public Status accept(Deque<Parser> stack, Parser parser, Lexeme lex) {
        if(parser != null && lex != null && ")".equals(((Symbol)lex).getValue())) {
            value = parser.getValue();
            return Status.READY;
        }
        return Status.FAIL;
    }

    @Override
    public Node<AstData> getValue() {
        return value;
    }
}
