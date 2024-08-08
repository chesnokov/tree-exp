package com.example.parse;

import com.example.ast.AstData;
import com.example.tree.Node;
import lombok.RequiredArgsConstructor;

import java.util.Deque;


@RequiredArgsConstructor
public class RootParser implements Parser {
    Parser current;

    @Override
    public Status accept(Deque<Parser> stack, Parser parser, Lexeme lex) {
        Parser active = getCurrent(stack);
        Status status = active.accept(stack, null, lex);
        if(status == Status.FAIL || status == Status.CONTINUE) {
            return status;
        }
        if(status == Status.POP) {
            if(stack.isEmpty()) {
                return Status.FAIL;
            } else {
                current = stack.pop();
                return Status.CONTINUE;
            }
        }
        do {
            if(stack.isEmpty()) {
                return status == Status.READY ? status : Status.FAIL;
            }
            current = stack.pop();
            status = current.accept(stack, active, status == Status.NEXT ? lex : null);
            active = current;
        } while(status == Status.READY || status == Status.NEXT);
        return status;
    }


    @Override
    public Node<AstData> getValue() {
        return current.getValue();
    }

    private Parser getCurrent(Deque<Parser> stack) {
        if(current == null) {
            current = stack.pop();
        }
        return current;
    }
}
