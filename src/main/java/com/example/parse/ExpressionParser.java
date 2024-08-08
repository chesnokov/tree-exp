package com.example.parse;

import com.example.ast.AstData;
import com.example.ast.AstNode;
import com.example.ast.DivideIntOperator;
import com.example.ast.IntNode;
import com.example.ast.MinusIntOperator;
import com.example.ast.MultiplyIntOperator;
import com.example.ast.OperatorNode;
import com.example.ast.PlusIntOperator;
import com.example.tree.Node;

import java.util.Deque;
import java.util.Optional;

import static com.example.ast.AstData.AstType.BINARY_OPERATOR;
import static com.example.ast.AstData.AstType.VALUE;
import static com.example.parse.Lexeme.LexemeType.INT_LITERAL;
import static com.example.parse.Lexeme.LexemeType.SYMBOL;
import static com.example.parse.Parser.Status.CONTINUE;
import static com.example.parse.Parser.Status.FAIL;
import static com.example.parse.Parser.Status.NEXT;

public class ExpressionParser implements Parser {

    private Node<AstData> value;

    private Node<AstData> current;

    private Node<AstData> right;

    @Override
    public Node<AstData> getValue() {
        return value;
    }

    @Override
    public Status accept(Deque<Parser> stack, Parser parser, Lexeme lex) {
        if(parser == null) {
            return acceptLexeme(stack, lex);
        } else {
            return acceptParser(stack, parser);
        }
    }

    private Status acceptParser(Deque<Parser> stack, Parser parser) {
        Node<AstData> data = parser.getValue();
        if(right != null) {
            return FAIL;
        } else if(current != null) {
            right = data;
        } else if(value != null) {
            right = data;
        } else {
            value = data;
        }
        return Status.CONTINUE;
    }

    private Status acceptLexeme(Deque<Parser> stack, Lexeme lex) {
        if(lex.getType() == INT_LITERAL) {
            return acceptLiteral(lex);
        } else if(lex.getType() == SYMBOL) {
            if("(".equals(((Symbol)lex).getValue())) {
                return acceptOpeningParent(stack);
            } else if(isOperator(lex)) {
                return acceptOperator(stack, lex);
            }
        }
        return acceptUnknownLexOrSymbol();
    }

    private boolean isOperator(Lexeme lex) {
        return lex.getType() == SYMBOL &&
                "+".equals(((Symbol)lex).getValue()) ||
                "-".equals(((Symbol)lex).getValue()) ||
                "*".equals(((Symbol)lex).getValue()) ||
                "/".equals(((Symbol)lex).getValue());
    }

    private Status acceptOpeningParent(Deque<Parser> stack) {
        stack.push(this);
        stack.push(new ExpressionParenthesesParser());
        stack.push(new ExpressionParser());
        return Status.POP;
    }

    private Status acceptUnknownLexOrSymbol() {
        if(value != null) {
            if(right != null) {
                if(current != null) {
                    ((OperatorNode) current).setRight(right);
                } else {
                    ((OperatorNode) value).setRight(right);
                }
                right = null;
                current = null;
                return NEXT;
            }
            if(current != null) {
                return FAIL;
            }
            if(AstNode.getType(value) == BINARY_OPERATOR &&
                    ((OperatorNode)value).getRight() == null) {
                return FAIL;
            }
            return NEXT;
        } else {
            return FAIL;
        }
    }

    private Status acceptOperator(Deque<Parser> stack, Lexeme lex) {
        if(value == null ||
                right == null && (current != null || AstNode.getType(value) == BINARY_OPERATOR && ((OperatorNode)value).getRight() == null)) {
            return acceptUnaryOperator(stack, lex);
        }
        Optional<Node<AstData>> operator = getBinaryOperator(lex);
        return operator.map(this::acceptOperator).orElse(FAIL);
    }

    private Status acceptUnaryOperator(Deque<Parser> stack, Lexeme lex) {
        return FAIL;
    }

    private Status acceptOperator(Node<AstData> operator) {
        if(value == null) {
            // this is a case of unary operator
            return FAIL;
        }
        if(AstNode.getType(value) == VALUE ||
            AstNode.getType(value) == BINARY_OPERATOR && right == null && current == null) {
            ((OperatorNode)operator).setLeft(value);
            value = operator;
            return CONTINUE;
        } else if(right == null) {
            // this is a case of unary operator
            return FAIL;
        } else {
            if(current == null) {
                if(AstNode.getPriority(operator) > AstNode.getPriority(value)) {
                    ((OperatorNode)value).setRight(operator);
                    ((OperatorNode)operator).setLeft(right);
                    current = operator;
                } else {
                    ((OperatorNode)value).setRight(right);
                    ((OperatorNode)operator).setLeft(value);
                    value = operator;
                }
                right = null;
                return CONTINUE;
            } else {
                if(AstNode.getPriority(operator) > AstNode.getPriority(value)) {
                    ((OperatorNode)current).setRight(right);
                    right = null;
                    ((OperatorNode)operator).setLeft(current);
                    current = operator;
                    ((OperatorNode)value).setRight(current);
                } else {
                    ((OperatorNode)current).setRight(right);
                    right = null;
                    ((OperatorNode)operator).setLeft(value);
                    value = operator;
                    current = null;
                }
                return Status.CONTINUE;
            }
        }
    }

    private Status acceptLiteral(Lexeme lex) {
        if(value == null) {
            value = new IntNode(((IntLiteral)lex).getValue());
            return CONTINUE;
        } else {
            if(right != null) {
                return FAIL;
            } else {
                right = new IntNode(((IntLiteral)lex).getValue());
                return CONTINUE;
            }
        }
    }

    private Optional<Node<AstData>> getBinaryOperator(Lexeme lex) {
        String operatorSymbol = ((Symbol)lex).getValue();
        return switch (operatorSymbol) {
            case "+" -> Optional.of(OperatorNode.builder().data(new PlusIntOperator()).build());
            case "-" -> Optional.of(OperatorNode.builder().data(new MinusIntOperator()).build());
            case "*" -> Optional.of(OperatorNode.builder().data(new MultiplyIntOperator()).build());
            case "/" -> Optional.of(OperatorNode.builder().data(new DivideIntOperator()).build());
            case null, default -> Optional.empty();
        };
    }
}
