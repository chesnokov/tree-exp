package com.example.parse;

import com.example.ast.AstData;
import com.example.tree.Node;

import java.util.Deque;

public class EofParser implements Parser {

    private Node<AstData> value;

    @Override
    public Status accept(Deque<Parser> stack, Parser parser, Lexeme lex) {
        Status status = Status.FAIL;
        if(lex.getType() == Lexeme.LexemeType.EOF && parser != null) {
            value = parser.getValue();
            status = Status.READY;
        }
        return status;
    }

    @Override
    public Node<AstData> getValue() {
        return value;
    }
}
