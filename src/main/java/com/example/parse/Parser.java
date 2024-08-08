package com.example.parse;

import com.example.ast.AstData;
import com.example.tree.Node;

import java.util.Deque;

public interface Parser {
    enum Status {
        CONTINUE,
        READY,
        NEXT,
        POP,
        FAIL
    }

    Status accept(Deque<Parser> stack, Parser parser, Lexeme lex);

    Node<AstData> getValue();
}
